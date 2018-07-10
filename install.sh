#!/usr/bin/env bash

{
  #------------------------------- USEFUL FUNCTIONS -----------------------------

  # center align
  # @param $1 text to center align
  # @param $2 row
  center() {
    tput cup $2 $(( ( $(tput cols) / 2 ) - ( ${#1} / 2) ))
  }

  # pretty prints the installer title
  title() {
    tput clear
    center 'V-Sim Installer' 0
    message 'V-Sim Installer' 1
    center "$(vsim_version)" 1
    message "$(vsim_version)" 4
    echo
  }

  # pretty prints a message
  # @param $1 message
  # @param $2 color [1-7]
  message() {
    tput bold
    tput setaf $2
    echo "$1"
    tput sgr0
  }

  # pretty prints an error
  # @param $1 error message
  error() {
    tput bold
    tput setaf 1
    >&2 echo "$1"
    tput sgr0
  }

  # verifies if a command exists
  # @param $1 command to verify
  has() {
    type "$1" > /dev/null 2>&1
  }

  # auto-detect profile
  detect_profile() {
    local DETECTED_PROFILE
    DETECTED_PROFILE=""
    # BASH
    if [ -n "${BASH_VERSION-}" ]; then
      if [ -f "$HOME/.bashrc" ]; then
        DETECTED_PROFILE="$HOME/.bashrc"
      elif [ -f "$HOME/.bash_profile" ]; then
        DETECTED_PROFILE="$HOME/.bash_profile"
      fi
    # ZSH
    elif [ -n "${ZSH_VERSION-}" ]; then
      if [ -f "$HOME/.zshrc" ]; then
        DETECTED_PROFILE="$HOME/.zshrc"
      fi
    fi
    # try again with .profile also
    if [ -z "$DETECTED_PROFILE" ]; then
      local PROFILES
      PROFILES='.profile .bashrc .bash_profile .zshrc'
      for PROFILE in $PROFILES; do
        if [ -f "$HOME/$PROFILE" ]; then
          DETECTED_PROFILE="$HOME/$PROFILE"
          break
        fi
      done
    fi
    # print detected profile
    if [ ! -z "$DETECTED_PROFILE" ]; then
      echo "$DETECTED_PROFILE"
    fi
  }

  # downloads a zip file
  # @param $1 zip url
  # @param $2 output name
  download() {
    if has 'curl'; then
      curl "$1" -L -o "$2" > /dev/null 2>&1
      return
    elif has 'wget'; then
      wget -O "$2" "$1" > /dev/null 2>&1
      return
    fi
    return 1
  }

  #------------------------------------- V-Sim --------------------------------

  # prints V-Sim version
  vsim_version() {
    echo 'v1.0.0'
  }

  # current V-Sim release link
  vsim_url() {
    local VERSION
    VERSION=$(vsim_version)
    echo "https://github.com/andrescv/V-Sim/releases/download/$VERSION/$VERSION.zip"
  }

  # V-Sim install directory
  vsim_dir() {
    echo "$HOME/.vsim"
  }

  # downloads V-Sim current release
  vsim_download() {
    tput sc
    message 'downloading release...' 3
    # no more programs
    download "$(vsim_url)" "vsim.zip" || {
      error "an error ocurred while downloading release $(vsim_version).zip"
      exit 1
    }
    tput rc
    message "release: $(vsim_version).zip downloaded" 2
  }

  # installs or updates V-Sim current release
  vsim_install() {
    local CWD
    CWD=$(pwd)
    # only install if java 8 (or later) is installed
    if ! has 'java'; then
      error 'you need Java SE 8 (or later) runtime environment (JRE) installed on your computer'
      exit 1
    fi
    # also test if curl or wget is available
    if ! has 'wget' || ! has 'curl'; then
      error 'you need curl or wget to install V-Sim'
      exit 1
    fi
    # and also unzip
    if ! has 'unzip'; then
      error 'you need unzip to install V-Sim'
    fi
    # create install directory
    if [ -d "$(vsim_dir)" ]; then
      message 'V-Sim installation directory already exists (updating)' 2
    else
      message 'creating V-Sim installation directory...' 2
      mkdir -p "$(vsim_dir)" > /dev/null 2>&1 || {
        error "an error ocurred while creating installation directory: $(vsim_dir)"
        exit 1
      }
    fi
    cd "$(vsim_dir)"
    # clean old files
    rm -rf lib LICENSE VSim.jar vsim
    # download current release
    vsim_download
    # unzip
    unzip vsim.zip > /dev/null 2>&1 || {
      error "can not unzip vsim.zip"
      rm -f vsim.zip
      exit 1
    }
    rm -f vsim.zip
    # create V-Sim launcher
    printf '#!/bin/sh\n' >> vsim
    printf "java -jar %s/VSim.jar %s" "$(vsim_dir)" '$@' >> vsim
    chmod 755 vsim
    # append source to profile
    local PROFILE
    PROFILE=$(detect_profile)
    local SOURCE_STR
    SOURCE_STR="\n# V-Sim\nexport PATH=\"\$PATH:$(vsim_dir)\"\n"
    # append source to profile
    if [ -z "$PROFILE" ]; then
      echo
      message "profile not found tried: ~/.bashrc, ~/.bash_profile, ~/.zshrc, and ~/.profile." 3
      message "create one of them and run this script again" 3
      message "or append the following lines to the correct file yourself:" 3
      printf "${SOURCE_STR}"
      echo
      message "then source it" 3
    else
      if ! command grep -qc "$(vsim_dir)" "$PROFILE"; then
        echo
        message "appending V-Sim source string to $PROFILE" 6
        printf "${SOURCE_STR}" >> "$PROFILE"
      else
        echo
        message "V-Sim source string already in ${PROFILE}" 6
      fi
      source $PROFILE
    fi
    echo
    cd "$CWD"
    message "=> all done" 2
  }
  # print title
  title
  # call V-Sim installer
  vsim_install
}
