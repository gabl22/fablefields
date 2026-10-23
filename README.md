# FableFields
 
A 2D top-down farming game written in Java with [libGDX](https://libgdx.com/). Explore a procedurally generated island, chop trees, mine ore, raise crops and animals, and complete tasks that unlock new plants.
 
Started as a school project at BRG Innsbruck (2026), roughly 140 hours of work in the first phase.
 
## Features
 
- **Procedural world:** 120x120 tile map generated from 2D simplex noise (water, sand, soil, grass), populated with trees, chickens and cows.
- **Farming:** till soil, plant seeds, water crops at the right growth stage, harvest. Yields are randomized, but on average you get back more seeds than you plant.
- **Animals:** chickens lay eggs, cows can be milked. Animals wander randomly and run when hit.
- **Ore mining:** iron, coal, diamond, gold and stone blocks shrink with each pickaxe hit and yield material.
- **Inventory:** stacked items, hotbar-style selection by mouse, number keys or Tab.
- **Task system:** guided intro tasks teach the mechanics, afterwards tasks are generated procedurally and reward seeds for new crops and gold coins.
- **Movement:** walking, sprinting, swimming, orthogonal sliding along obstacles.
- **Feedback:** context-sensitive cursors and tooltips (e.g. "CHOP", "WATER Carrot").
- **Camera:** zoom, drag to pan, `C` to snap back to the player.
## Controls
 
| Input | Action |
|---|---|
| `W` `A` `S` `D` | Move |
| `Left Shift` | Sprint (not while swimming) |
| Left click | Use the selected item on the target tile or entity |
| Left click, no item selected | Collect eggs, milk cows |
| Number keys / `Tab` / click on stack | Select inventory stack |
| Mouse wheel | Zoom |
| Click and drag | Pan the camera |
| `C` | Re-center camera on the player |
 
## Running
 
Requirements: a recent JDK (JDK 21 was used during development). Gradle is provided through the wrapper.
 
```
git clone https://github.com/gabl22/fablefields.git
cd fablefields
./gradlew lwjgl3:run        # Windows: gradlew.bat lwjgl3:run
```
 
## Technical notes
 
- **Architecture:** game logic runs independently from rendering. The world is a tile grid on three logical layers, converted by an intermediate component into libGDX tiled-map layers for drawing.
- **Tall plants:** fully grown plants are taller than one tile. A plant tile emits two render instructions, the lower part on its own tile and the upper part on the tile above, drawn over whatever is there.
- **Depth sorting:** non-grid entities (player, animals, trees) are drawn by vertical screen position, which gives the impression of depth.
- **Collision:** tile walkability checks plus a quadtree for static game objects. A blocked movement loses only the blocked axis, so entities slide along obstacles.
- **Crop growth:** each plant schedules its own growth step. Delta times from the game loop are accumulated until the stage duration is reached. Stages that need water schedule the next step only after a watering action.
- **Tasks and events:** game actions publish events to a central event bus. Active tasks listen, check their progress, and trigger follow-up actions (reward, next task, item removal).
- **Animal behavior:** small state machine (idle, walking, running) with random transitions and random redirection when blocked.
- **Assets:** a custom asset loader handles the row-based animation sheets of the graphics pack. All tiles and items are addressed by string IDs (for example `item/axe`, `tile/plant/lettuce/stage/2`) mapped to tileset positions in a JSON file. Assets are loaded up front, loading on the fly is logged as a warning.
- **Localization:** all displayed text comes from a language file.
## Known limitations
 
- Prototype scope: no end goal, the game is an open sandbox apart from the tasks.
- Game balance (growth times, rewards, drop rates) has not been tuned systematically.
- Animations play at a fixed position, so the watering animation can appear next to the targeted tile (the effect itself is applied correctly).
- No save/load yet: the world is regenerated on every start, progress completely resets.
## Credits
 
Graphics: [Sunnyside World](https://danieldiggle.itch.io/sunnyside) by Daniel Diggle, used under the license shipped with the pack (see `assets/credits.txt`). The cursors and the gold coin icon were drawn for this project.
