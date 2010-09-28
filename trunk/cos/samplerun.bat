# SJF Td=15ms Ts=1ms simulate 48500ms
java edu.rpi.cs.os.queuingpolicy.Scheduler -sjf 30 2 97000

# HRRN Td=15ms Ts=1ms simulate 50000ms
java edu.rpi.cs.os.queuingpolicy.Scheduler -hrrn 30 2 100000

# RR Tq=1.5ms Td=6ms Ts=1ms simulate 50000ms
java edu.rpi.cs.os.queuingpolicy.Scheduler -rr3 12 2 100000
