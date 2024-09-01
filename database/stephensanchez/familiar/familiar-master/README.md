familiar
========

Warning: This repository is still under rapid development. Sweeping changes will be made frequently.

To Run:
gradle jettyRun

To access the beginnings of a RESTful interface:
http://localhost:8080/familiar/api/v0/characters

What do I want to do with this repo?
* allow character sheets to be created
* allow character sheets to be shared to DM
* allow stats to be shared based on rules
* Allow auto-generation of sheets based on rules
* Configure macro actions to look up relative sheets and die rolls, modifiers, etc
* Tracking and logs to help review a session history

New Idea & Main Focus: D&D Combat simulator!!! Make something that can simulate fights based on character sheets and monsters, in order to tweak the difficulty of each encounter on-the-fly so I can keep the fights challenging. It's my greatest weakness as a DM and I'd love to see how to build it.

* Most of the stats for monsters are publicly available information online
* Characters can be generated easily enough
* The trick is to build a priority rule system on all the abilities available to a character/creature
* Spatial evaluation of a board also changes priority of abilities, but may be a feature added on afterwards.
* The idea is to simulate a fight multiple times to get a general difficulty rating

Additional Notes
* Dungeons are trickier -- need a way to evaluate a series of encounters, or encounter "trees", where a combination of encounters could occur, and characters have a sense of conserving spells that will not regenerate (definitely future-future feature)

