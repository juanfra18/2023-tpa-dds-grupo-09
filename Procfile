release: if [ "$SEED_DONE" != "true" ]; then java $JAVA_OPTS -cp target/ejercicio-1.0-SNAPSHOT-jar-with-dependencies.jar models.persistence.Seed && export SEED_DONE=true; fi
web: java $JAVA_OPTS -cp target/ejercicio-1.0-SNAPSHOT-jar-with-dependencies.jar server.App
