/*
Copyright (C) 2018-2019 Andres Castellanos

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>
*/

package jupiter.gui;

import javafx.scene.text.Font;


/** Jupiter fonts loader. */
public final class Fonts {

  /** Loads Jupiter fonts. */
  public static void load() {
    anonymous();
    montserrat();
    ubuntu();
  }

  /** Loads Anonymous Pro font. */
  private static void anonymous() {
    Font.loadFont(Fonts.class.getResource("/jupiter/fonts/Anonymous_Pro/AnonymousPro.ttf").toExternalForm(), 10);
    Font.loadFont(Fonts.class.getResource("/jupiter/fonts/Anonymous_Pro/AnonymousPro-Bold.ttf").toExternalForm(), 10);
  }

  /** Loads Montserrat font. */
  private static void montserrat() {
    Font.loadFont(Fonts.class.getResource("/jupiter/fonts/Montserrat/Montserrat-Medium.ttf").toExternalForm(), 10);
    Font.loadFont(Fonts.class.getResource("/jupiter/fonts/Montserrat/Montserrat-Regular.ttf").toExternalForm(), 10);
    Font.loadFont(Fonts.class.getResource("/jupiter/fonts/Montserrat/Montserrat-SemiBold.ttf").toExternalForm(), 10);
  }

  /** Loads Ubuntu Mono font. */
  private static void ubuntu() {
    Font.loadFont(Fonts.class.getResource("/jupiter/fonts/Ubuntu_Mono/UbuntuMono-Italic.ttf").toExternalForm(), 10);
    Font.loadFont(Fonts.class.getResource("/jupiter/fonts/Ubuntu_Mono/UbuntuMono-Regular.ttf").toExternalForm(), 10);
  }

}
