#! /bin/bash


# Check if postgres already exists
which -a psql
if [ "$?" != "0" ];
then
    # Step 1: Add PostgreSQL Repository

    sudo apt-get install wget ca-certificates

    wget --quiet -O - https://www.postgresql.org/media/keys/ACCC4CF8.asc | sudo apt-key add -

    sudo sh -c 'echo "deb http://apt.postgresql.org/pub/repos/apt/ `lsb_release -cs`-pgdg main" >> /etc/apt/sources.list.d/pgdg.list'

    # Step 2: Update the Package List

    sudo apt-get update

    # Step 3: Install PostgreSQL

    sudo apt-get -y install postgresql postgresql-contrib

fi

# Edit postgresql configuration file in order to connect without password
#TODO sudo vim /etc/postgresql/13/main/pg_hba.conf

# Restart postgres service
# sudo /etc/init.d/postgresql restart

# Create a database

CONN_USER="postgres"

sudo -i -u ${CONN_USER} bash -c 'psql -c "CREATE DATABASE test"'

