## install

```sh
brew install k6
```

## run script

```sh
k6 run script.js
k6 run --out json=result.json script.js
K6_WEB_DASHBOARD=true k6 run script.js
K6_WEB_DASHBOARD=true k6 run --out json=result.json script.js
```