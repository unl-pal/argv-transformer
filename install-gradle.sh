#!/bin/bash

set -e

# Gradle version
GRADLE_VERSION=8.11.1
GRADLE_DIST=gradle-$GRADLE_VERSION-bin.zip
GRADLE_URL=https://services.gradle.org/distributions/$GRADLE_DIST

# Install dependencies
sudo apt update
sudo apt install -y wget unzip

# Download Gradle
wget -q $GRADLE_URL -O /tmp/$GRADLE_DIST

# Create installation directory
sudo mkdir -p /opt/gradle
sudo unzip -d /opt/gradle /tmp/$GRADLE_DIST

# Symlink to make version switch easy
sudo ln -sfn /opt/gradle/gradle-$GRADLE_VERSION /opt/gradle/latest

# Add to PATH if not already present
PROFILE_SCRIPT=/etc/profile.d/gradle.sh
sudo tee $PROFILE_SCRIPT > /dev/null <<EOF
export PATH=/opt/gradle/latest/bin:\$PATH
EOF
sudo chmod +x $PROFILE_SCRIPT

# Apply changes to current shell
source $PROFILE_SCRIPT

# Verify installation
gradle -v