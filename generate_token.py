import secrets
from shutil import move
from os import remove

if __name__ == "__main__":
    secret = secrets.token_urlsafe(16)
    with open('digitalnx_web/tmp', "w+") as t_f:
        with open('digitalnx_web/__init__.py', 'r') as o_f:
            for line in o_f.readlines():
                if "<SECRET_KEY>" in line:
                    line = line.replace("<SECRET_KEY>", secret)
                t_f.write(line)
    remove('digitalnx_web/__init__.py')
    move("digitalnx_web/tmp", "digitalnx_web/__init__.py")
