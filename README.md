# amplitude-template

Use `seancorfield/clj-new` to create a basic project that uses AWS Amplify, Clojurescript, Shadow-Cljs and Re-Frame

## Usage

### Set up an alias for clj-new 

In your `.clojure/deps.edn`:

```clj
    {:aliases
     {:new {:extra-deps {seancorfield/clj-new
                         {:mvn/version "0.8.6"}}
            :main-opts ["-m" "clj-new.create"]}}
     ...}
```

### Generate the project from a local git clone of the amplitude-template repo

After you cloned the repo to your local disk:

clj -A:new  <path-to-amplitude-template repo>::amplitude-template omnywayLabs/testProject

__NOTE:__ do not have dashes or underscores in the project group / name. Stupid Javascript freaks out 

## License
MIT License
Copyright © 2020 Omnyway, Inc.
