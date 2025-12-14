Database connection notes

If you get the error "Argument 'iteration must be >= 4096'" when the application attempts to connect, it usually means the SCRAM authentication parameters returned by the server (or a pooler) use too few iterations for the JDBC driver's minimum check. This commonly happens when connecting via a pooler or proxy (for example Neon connection poolers).

Workarounds:

- Use the "direct" / compute-node endpoint supplied by your Neon database (bypass the pooler). In the Neon web UI look for an option like "Connect" → "Direct connection" and copy the JDBC URL.

- Configure connection information via environment variables instead of relying on hard-coded values. The app reads the following variables if set:
  - `DB_URL` (e.g. `jdbc:postgresql://<direct-host>/<dbname>?sslmode=require`)
  - `DB_USER`
  - `DB_PASSWORD`

Example on Windows cmd:

  set DB_URL=jdbc:postgresql://<direct-host>/<dbname>?sslmode=require
  set DB_USER=your_user
  set DB_PASSWORD=your_password
  java -jar target/brasil-burger-jar-with-dependencies.jar

If the problem persists, consult your DB provider support (Neon) to obtain an endpoint with acceptable SCRAM parameters or ask them for guidance about direct connection options.

Security note: avoid committing credentials into source control. Use environment variables or a secret manager for production.
