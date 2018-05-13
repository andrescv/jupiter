class RegistersConfig:

    registers = [
        {
            'ABIName': 'zero',
            'description': 'hard-wired zero',
            'saver': '-'
        },
        {
            'ABIName': 'ra',
            'description': 'return address',
            'saver': 'caller'
        },
        {
            'ABIName': 'sp',
            'description': 'stack pointer',
            'saver': 'callee'
        },
        {
            'ABIName': 'gp',
            'description': 'global pointer',
            'saver': '-'
        },
        {
            'ABIName': 'tp',
            'description': 'thread pointer',
            'saver': '-'
        },
        {
            'ABIName': 't0',
            'description': 'temporary / alternate link register',
            'saver': 'caller'
        },
        {
            'ABIName': 't1',
            'description': 'temporary',
            'saver': 'caller'
        },
        {
            'ABIName': 't2',
            'description': 'temporary',
            'saver': 'caller'
        },
        {
            'ABIName': 's0',
            'alt_name': 'fp',
            'description': 'saved register / frame pointer',
            'saver': 'callee'
        },
        {
            'ABIName': 's1',
            'description': 'saved register',
            'saver': 'callee'
        },
        {
            'ABIName': 'a0',
            'description': 'function argument / return value',
            'saver': 'caller'
        },
        {
            'ABIName': 'a1',
            'description': 'function argument / return value',
            'saver': 'caller'
        },
        {
            'ABIName': 'a2',
            'description': 'function argument',
            'saver': 'caller'
        },
        {
            'ABIName': 'a3',
            'description': 'function argument',
            'saver': 'caller'
        },
        {
            'ABIName': 'a4',
            'description': 'function argument',
            'saver': 'caller'
        },
        {
            'ABIName': 'a5',
            'description': 'function argument',
            'saver': 'caller'
        },
        {
            'ABIName': 'a6',
            'description': 'function argument',
            'saver': 'caller'
        },
        {
            'ABIName': 'a7',
            'description': 'function argument',
            'saver': 'caller'
        },
        {
            'ABIName': 's2',
            'description': 'saved register',
            'saver': 'callee'
        },
        {
            'ABIName': 's3',
            'description': 'saved register',
            'saver': 'callee'
        },
        {
            'ABIName': 's4',
            'description': 'saved register',
            'saver': 'callee'
        },
        {
            'ABIName': 's5',
            'description': 'saved register',
            'saver': 'callee'
        },
        {
            'ABIName': 's6',
            'description': 'saved register',
            'saver': 'callee'
        },
        {
            'ABIName': 's7',
            'description': 'saved register',
            'saver': 'callee'
        },
        {
            'ABIName': 's8',
            'description': 'saved register',
            'saver': 'callee'
        },
        {
            'ABIName': 's9',
            'description': 'saved register',
            'saver': 'callee'
        },
        {
            'ABIName': 's10',
            'description': 'saved register',
            'saver': 'callee'
        },
        {
            'ABIName': 's11',
            'description': 'saved register',
            'saver': 'callee'
        },
        {
            'ABIName': 't3',
            'description': 'temporary',
            'saver': 'caller'
        },
        {
            'ABIName': 't4',
            'description': 'temporary',
            'saver': 'caller'
        },
        {
            'ABIName': 't5',
            'description': 'temporary',
            'saver': 'caller'
        },
        {
            'ABIName': 't6',
            'description': 'temporary',
            'saver': 'caller'
        },
    ]
