all: dev

deps:
	yarn

compile:
	yarn && yarn compile

release:
	yarn
	yarn release
	cp -r public/css dist/css
	cp -r public/fonts dist/fonts
	cp -r public/index.html dist/index.html

dev:
	yarn
	shadow-cljs watch app

clean:
	rm -rf target; rm -rf public/js
	rm -f yarn.lock
	rm -rf .shadow-cljs
	rm -f package-lock.json
	rm -rf .cpcache
	rm -rf node_modules
	rm -rf dist

configure:
	amplify configure

gql: graphql

graphql:
	cat etc/schema/schema.graphql >> amplify/backend/api/amplitude/schema.graphql
	amplify api gql-compile
	amplify push
