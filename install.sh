#!/bin/sh

{
  # verify if some cmd exists
  has() {
    type "$1" > /dev/null 2>&1
  }

  # auto-detect profile
  detect_profile() {
    local DETECTED_PROFILE
    DETECTED_PROFILE=""
    if [ -n "${BASH_VERSION-}" ]; then
      if [ -f "$HOME/.bashrc" ]; then
        DETECTED_PROFILE="$HOME/.bashrc"
      elif [ -f "$HOME/.bash_profile" ]; then
        DETECTED_PROFILE="$HOME/.bash_profile"
      fi
    elif [ -n "${ZSH_VERSION-}" ]; then
      DETECTED_PROFILE="$HOME/.zshrc"
    fi
    if [ ! -z "$DETECTED_PROFILE" ]; then
      echo "$DETECTED_PROFILE"
    fi
  }

  # install vsim
  install() {
    # detect java
    if ! has java; then
      echo >&2 "you need Java SE 8 (or later) runtime environment (JRE) installed on your computer."
      exit 1
    fi
    # detect wget or curl
    if ! has wget || ! has curl || ! has unzip; then
      echo >&2 "you need curl or wget and unzip to install vsim"
      exit 1
    else
      local PROFILE
      PROFILE="$(detect_profile)"
      local INSTALL_DIR
      INSTALL_DIR="$HOME/.vsim"
      local SOURCE_STR
      SOURCE_STR="\\n# VSim\\nexport PATH=\"\$PATH:${INSTALL_DIR}\"\\n"
      # create installation directory
      mkdir -p $INSTALL_DIR
      cd $INSTALL_DIR
      # remove old files if any
      rm -rf lib vsim VSim.jar
      # download current version
      if has wget; then
        wget -O vsim.zip https://www.dropbox.com/s/7gtqsej70u4zljl/vsim-v1.0.0-beta.zip?dl=1
      else
        curl https://www.dropbox.com/s/7gtqsej70u4zljl/vsim-v1.0.0-beta.zip?dl=1 --output vsim.zip
      fi
      # unpack and create executable script
      unzip vsim.zip
      rm -f vsim.zip
      echo '#!/bin/sh' >> vsim
      echo 'java -jar ~/.vsim/VSim.jar $@' >> vsim
      chmod 755 vsim
      # append source to profile
      if [ -z $PROFILE ]; then
        echo "=> profile not found. tried ~/.bashrc, ~/.bash_profile, ~/.zshrc, and ~/.profile."
        echo "=> create one of them and run this script again"
        echo "   or"
        echo "=> append the following lines to the correct file yourself:"
        command printf "${SOURCE_STR}"
        echo "=> then source it"
        echo
      else
        if ! command grep -qc "$INSTALL_DIR" "$PROFILE"; then
          echo "=> appending vsim source string to $PROFILE"
          command printf "${SOURCE_STR}" >> "$PROFILE"
        else
          echo "=> vsim source string already in ${PROFILE}"
        fi
        source $PROFILE
      fi
    fi
  }
  # call installer
  install
}
