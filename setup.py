from setuptools import setup

setup(
    name="depointed",
    version="0.0.1",
    description="depointed performs optical character recognition for single characters", 
    author="Nathan Smith",
    author_email="depointed@nfsmith.ca",
    url="https://github.com/nsmith5/depointed",
    license="GPL3+",
    packages=['depointed'],
    install_requires=[
        'flask',
    ],
    scripts=['bin/depointed']
)
