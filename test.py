'''
Copyright (C) 2018 Andres Castellanos

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
'''

import os
import sys
import subprocess
from glob import glob

newline = os.linesep
CMD = 'java -jar VSim.jar -nocolor %s'


def main():
    print(newline + 'Testing VSim....' + newline)
    # just try to run every program
    total = 0
    nerror = 0
    nsuccess = 0
    errors = []
    try:
        for f in sorted(glob('test/*')):
            # ignore readme and license files
            if not f.endswith('.s'):
                continue
            total += 1
            command = CMD % f
            process = subprocess.Popen(command, shell=True, stdout=subprocess.PIPE)
            process.wait()
            rcode = process.returncode
            if rcode != 0:
                nerror += 1
                print('    ' + f + ':test%02d ✘' % rcode)
                errors.append('    ' + f + ':test%02d ✘' % rcode)
            else:
                print('    ' + f + ': ✔')
                nsuccess += 1
        print(newline + '%d/%d tests passed' % (nsuccess, total))
        if len(errors) == 0:
            print(newline + 'All done...')
        else:
            print(newline + 'fail...' + newline)
            print('Summary:' + newline)
            for error in errors:
                print(error)
            print('')
            sys.exit(1)
    except KeyboardInterrupt:
        print(newline + 'tests interrupted...' + newline)
        sys.exit(1)


if __name__ == '__main__':
    main()
