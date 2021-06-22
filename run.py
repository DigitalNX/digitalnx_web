from digitalnx_web import app, frontend_dir_path, frontend_port
from digitalnx_web.api.schedules import run_scheduler
import subprocess, os, sched, time
import atexit, signal

def kill_subprocess(pid):
    if pid is None:
        pass
    else:
        os.kill(pid, signal.SIGTERM)

def start_frontend():
    if os.environ.get('WERKZEUG_RUN_MAIN') != 'true':
        DEVNULL = open(os.devnull, 'w')
        print('Starting frontend server: ' + 'http://127.0.0.1:' + str(frontend_port) + ' ...')
        proc = subprocess.Popen(["npm", "run", "serve", "--", "--port", str(frontend_port)],
            cwd=frontend_dir_path, stdout=DEVNULL, stderr=subprocess.STDOUT)
        pid = proc.pid
        atexit.register(kill_subprocess, pid)

def start_schedules():
    if os.environ.get('WERKZEUG_RUN_MAIN') != 'true':
        run_scheduler()

with app.app_context():
    start_frontend()
    start_schedules()

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0')
