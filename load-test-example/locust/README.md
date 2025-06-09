## install

```sh
pip install locust
```

## run script
```sh
locust -f locustfile.py --headless -u 1 -r 1 -t 1m --host=http://localhost:8080
python3 -m locust -f locustfile.py --headless -u 1 -r 1 -t 1m --host=http://localhost:8080
python3 -m locust -f locustfile.py -u 1 -r 1 -t 1m --host=http://localhost:8080
```