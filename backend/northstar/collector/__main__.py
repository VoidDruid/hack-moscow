from collector import setup
setup()

from collector.loop import loop, collect, process_value, redis

print('Starting collector...')

#loop()
collect()
