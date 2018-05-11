###ab 命令压力测试
#### 执行
进入工程跟目录，执行如下命令
```
ab -k -T "text/xml" -H 'soapUrl:http://192.168.0.121:8092/services/CommonService' -H 'soapAction:' -p ./testDir/ab_post.xml -n 1000 -c 100 http://localhost:8089/emsb/esb/call2
```
#### 结果
```
This is ApacheBench, Version 2.3 <$Revision: 1757674 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking localhost (be patient)
Completed 100 requests
Completed 200 requests
Completed 300 requests
Completed 400 requests
Completed 500 requests
Completed 600 requests
Completed 700 requests
Completed 800 requests
Completed 900 requests
Completed 1000 requests
Finished 1000 requests


Server Software:
Server Hostname:        localhost
Server Port:            8089

Document Path:          /emsb/esb/call2
Document Length:        239 bytes

Concurrency Level:      100
Time taken for tests:   3.026 seconds
Complete requests:      1000
Failed requests:        0
Keep-Alive requests:    1000
Total transferred:      378000 bytes
Total body sent:        329000
HTML transferred:       239000 bytes
Requests per second:    330.42 [#/sec] (mean)
Time per request:       302.643 [ms] (mean)
Time per request:       3.026 [ms] (mean, across all concurrent requests)
Transfer rate:          121.97 [Kbytes/sec] received
                        106.16 kb/s sent
                        228.13 kb/s total

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0    0   0.7      0       3
Processing:    13  147 107.7    136    3023
Waiting:       13  147 107.7    136    3023
Total:         13  147 107.8    136    3026

Percentage of the requests served within a certain time (ms)
  50%    136
  66%    162
  75%    180
  80%    194
  90%    227
  95%    255
  98%    281
  99%    307
 100%   3026 (longest request)
```
