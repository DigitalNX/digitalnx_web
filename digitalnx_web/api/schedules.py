# TODO : better internet api interface for scheduling (with OOP)
from .models import TCPRelaySchedule
from .routes.relays import relay_control_
import threading, time
from flask import Flask, request, current_app

threads = []
def run_scheduler():
    run_relay_schedules()

def run_relay_schedules():
    upcomingExecs = TCPRelaySchedule.upcomingExecutions()
    for i,e in enumerate(upcomingExecs):
        t = threading.Timer(e['nextExecution'], relay_execution, (e['relay_id'], e['period'], e['executionDuration']))
        threads.append({
            'relay_id': e['relay_id'],
            'thread': t
        })
        t.start()

        print("Adding relay function " + str(i) + " to scheduler...")

def run_relay_schedule(relay_id):
    print("[Scheduler] Adding new schedule task for relay " + str(relay_id) + ".")
    if not TCPRelaySchedule.controlledBySchedule(relay_id):
        for to in threads:
            if to['relay_id'] == relay_id:
                print("[Scheduler] Closing thread for relay " + str(relay_id) + ".")
                to['thread'].close()

    e = TCPRelaySchedule.get_scheduled_relay(relay_id)
    t = threading.Timer(e['nextExecution'], relay_execution, (e['relay_id'], e['period'], e['executionDuration']))
    threads.append({
        'relay_id': e['relay_id'],
        'thread': t
    })
    t.start()

def relay_execution(relay_id, period, executionDuration):
    relay_control_(relay_id, 'on')
    print("[Scheduler] Turning relay " + str(relay_id) + " on.")
    time.sleep(executionDuration)
    relay_control_(relay_id, 'off')
    print("[Scheduler] Turning relay " + str(relay_id) + " off.")

    # Continue the execution only if the relay is still controlled by the schedule.
    if TCPRelaySchedule.controlledBySchedule(relay_id):
        threading.Timer(period, relay_execution, (relay_id, period, executionDuration)).start()