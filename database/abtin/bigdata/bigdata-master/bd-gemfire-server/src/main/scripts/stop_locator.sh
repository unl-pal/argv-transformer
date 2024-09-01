#!/usr/bin/env bash
gfsh -e "connect" -e "stop locator --name=locator$1"