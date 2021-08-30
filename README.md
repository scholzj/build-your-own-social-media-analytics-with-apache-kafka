# Build your own social media analytics with Apache Kafka

This repository contains the demo for a conference call with the same name.
You can also have a look at the [slides](https://docs.google.com/presentation/d/18bmiZagwrAe8fnuuyBs45l1U5OHIGwK9pjLALpcC23E/edit?usp=sharing).




## Support commands

```
kafkacat -G test-status -C -b 192.168.1.72:30935 -o beginning -t scholzj-timeline | jq .text
kafkacat -G full-status -C -b 192.168.1.72:30935 -o start -t scholzj-timeline | jq
```