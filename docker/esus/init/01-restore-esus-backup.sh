#!/bin/sh
set -e

BACKUP_FILE="/docker-entrypoint-initdb.d/20250107114431-esus-postgres.backup"

if [ -f "$BACKUP_FILE" ]; then
  echo "Restoring e-SUS database from $BACKUP_FILE..."
  if ! pg_restore \
    --username "$POSTGRES_USER" \
    --dbname "$POSTGRES_DB" \
    --no-owner \
    --no-acl \
    "$BACKUP_FILE"; then
    echo "pg_restore finished with warnings/errors. Check the postgres_esus logs if database objects are missing."
  fi
else
  echo "Backup file not found: $BACKUP_FILE"
fi
