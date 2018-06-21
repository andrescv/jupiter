import os
import sys
from glob import glob


CMD = 'java -jar VSim.jar -nocolor %s 1>/dev/null'


def main():
    error = False
    print('')
    print('Testing VSim....')
    print('')
    # just try to run every
    total = 0
    nerror = 0
    nsuccess = 0
    errors = []
    for f in sorted(glob('test/*/*')):
        total += 1
        rcode = os.system(CMD % f)
        if rcode != 0:
            nerror += 1
            error = True
            print('    ' + f + ': ✘')
            errors.append('    ' + f + ': ✘')
        print('    ' + f + ': ✔')
        nsuccess += 1
    print('')
    print('%d/%d tests passed' % (nsuccess, total))
    if not error:
        print('All done...')
    else:
        print('fail...')
        print('')
        print('Summary:')
        print('')
        for error in errors:
            print(error)
        sys.exit(1)


if __name__ == '__main__':
    main()
