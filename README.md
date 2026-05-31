# Mining Dimensions (NeoForge 1.21.1)

Custom mining/hunting/caving/climbing/nethering/sky dimensions with teleporters, portal blocks, teleport pads, extra ores, and hunting-dimension mob boosts.

Ported to NeoForge 1.21.1 with feature parity to Fabric 1.8.3.

## Dimensions

| Dimension | Description |
|-----------|-------------|
| **Mining** | Desert and plains biomes, always noon; Overworld ores and structures |
| **Climbing** | Badlands and windswept hills, always noon; Overworld ores and structures |
| **Hunting** | Dark plains with boosted, armored mobs; vanilla ores only |
| **Caving** | Mushroom cave biome; safe haven with bats and occasional spider spawners |
| **Nethering** | Vanilla Nether generation for modded-nether-friendly resource gathering |
| **Sky** | Daytime void dimension (1.21+) |

## Travel Methods

1. **Portable Teleporters** — Right-click to teleport between Overworld and a dimension. Sky teleporter places an oak plank under your feet on arrival.
2. **Portal Blocks** — Place a portal block and right-click to teleport. Searches a 3×3 chunk area (configurable to 5×5) for a matching portal block at the destination; places one under your feet if none is found.
3. **Teleport Pads** — One-way teleport to any captured location. Use the Spatial Capture Tool to set a target, then right-click the pad.

## Requirements

- Java 21
- Gradle wrapper (`gradlew` / `gradlew.bat`)

## Build

```bash
./gradlew build
```

## Run

```bash
./gradlew runClient
./gradlew runServer
```

## Configuration

Common config is generated at `config/mining_dims-common.toml` on first run.

Key options:
- `enableTeleportPad` — Enable/disable teleport pad system
- `enableExtendedSearchRange` — Use 25-chunk portal block search instead of 9-chunk
- `enableMessageOnSneak` / `enableMessageOnTeleport` — Toggle action bar messages
- Hunting dimension mob attribute and gear drop settings

## License

MIT
