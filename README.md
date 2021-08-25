# Build your own social media analytics with Apache Kafka

This repository contains the demo for a conference call with the same name.




## Support commands

```
kafkacat -G test-status -C -b 192.168.1.72:30935 -o beginning -t scholzj-timeline | jq .text
kafkacat -G full-status -C -b 192.168.1.72:30935 -o start -t scholzj-timeline | jq
```