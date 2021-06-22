import json
import urllib.request
from socket import timeout
import threading
import random, string
import time

def getJSON(url, timeout=0.5):
    return json.load(urllib.request.urlopen(url, timeout=timeout))

cache_thread_init = False

def add_api_cache(url):
    cached_data = {}
    for elem in add_api_cache.cached_urls:
        if elem['url'] == url:
            cached_data = elem

    if cached_data:
        if cached_data['available']:
            return cached_data['response']
        else:
            return {}
    else:
        url_file_dict = {'url': url, 'response': None, 'available': False}
        add_api_cache.cached_urls.append(url_file_dict)
        if not cache_thread_init:
            api_cache()
        while add_api_cache.cached_urls[-1]['available'] == False:
            time.sleep()
        return add_api_cache.cached_urls[-1]['response']           

add_api_cache.cached_urls = []

def api_cache():
    for data in add_api_cache.cached_urls:
        data['response'] = getJSON(data['url'])
        data['available'] = True
        threading.Timer(10, api_cache).start()