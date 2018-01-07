from setuptools import setup, find_packages

VERSION = 18.01

README = open('README.rst').read()

REQUIREMENTS = []

setup(
    name='VSim',
    version=VERSION,
    author='Andrés Castellanos',
    author_email='andres.cv@galileo.edu',
    url='https://github.com/andrescv/VSim',
    description='RISC-V Assembler and Runtime Simulator',
    long_description=README,
    license='GNU General Public License v3 (GPLv3)',
    classifiers=[
        'Intended Audience :: Education',
        'Topic :: Software Development :: Assemblers',
        'License :: OSI Approved :: GNU General Public License v3 (GPLv3)',
        'Programming Language :: Python :: 3.6'
    ],
    keywords='assembler simulator risc-v riscv-asm rv32im',
    packages=find_packages(exclude=['test']),
    install_requires=REQUIREMENTS
)
