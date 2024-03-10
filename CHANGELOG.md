# Changelog

## [0.4.4] - 2024-03-10

### Fixed

- Unlock Registrate version range

## [0.4.3] - 2023-11-25

### Fixed

- Fixed crash due to no will when using SentientWrath

## [0.4.2] - 2023-11-23

### Fixed

- Fixed Particle Type Registration on server side. Server wont crash anymore

## [0.4.1] - 2023-11-19

### Changed

- Updated glyph documentation for Sentient Harm and Sentient Wrath.

## [0.4.0] - 2023-11-17

### Added

- Sentient Wrath Glyph - Tier 2 spell that marks the target when hit. If a target is already marked, it does a heavy
  attack on it and its surrounding. It also gives small bonuses to player on kill

### Changed

- Sentient Harm is slightly changed. When using it along with `Duration` Augments, the glyph becomes into a effect spell
  and doesn't do the initial scaling damage.
- Sentient Harm Recipe now uses Sentient Sword instead of Petty Gem
- Selection of Will type for attacks have changed. Earlier they used the type that has the most amount in the player
  inventory. Now it is a priority based system based on slot and inventory position.

## [0.3.3] - 2023-11-14

### Fixed

- Fixed a crash when player doesn't have any will in inventory.

## [0.3.2] - 2023-11-04

### Changed

- Updated textures for Heretics Armor! Thanks Gronglegrowth/Eset!
- Sentient Harm Glyph now consumes will when on successful damage. The values are similar to Sentient Sword.
- Tome of Blood should now remember the old spells when crafting (the blood altar one works for now while the alchemy
  table recipes need an upstream(Blood Magic) fix)

## [0.3.1] - 2023-10-30

### Changed

- Removed the experience reduction of Heretic's Armor which caused upgrade tomes to be buggy
- Changed Mana Attunement discount calculation to fix 0 spell cost due to integer conversion

## [0.3.0] - 2023-10-28

Configs have changed. It is recommended to delete the old `tomeofblood` config and let it regenerate.

### Added

- Basic documentation in patchouli based `worn_notebook`

### Changed

- Reworked Tome of blood. It now lets you cast without mana limits. It uses mana first and then LP.
- Tome of blood textures have been updated!
- Living Mage Armor is now called Heretic's Armor
- Heretic's Armor now gains experience slower than its normal blood magic counterpart (exact scale configurable in
  configs)
- Default color for Heretic's Armor is now Red instead of Purple
- Mana Attunement trait has been rebalanced (Documentation has also been added)

## [0.2.1] - 2023-10-09

### Fixed

- Elytra upgrade now works with Living Mage Armor

## [0.2.0] - 2023-10-07

Configs have changed. It is recommended to delete the old `tomeofblood` config and let it regenerate.

### Changed

- Built against Neoforge. Requires newer versions of Ars Nouveau and Blood Magic
- Rebalanced Sentient Harm Glyph. Now it is more inline with base Harm Glyph but still has its special characteristics

### Fixed

- Sentient Harm Glyph causing server crash due to older version of Ars Nouveau

## [0.1.2] - 2023-09-30

### Added

- New textures for Living Mage Armor (Thanks to Gronglegrowth!)

### Fixes

- Fixed living armor modifiers not applying

## [0.1.1] - 2023-09-13

### Added

- recipes for Tome of Blood upgrades
- recipe of Living Mage Armor


## [0.1.0] - 2023-09-09

### Added

- Initial build
- 3 tiers/versions of Tome of Blood
- Glyph of Sentient Harm
- Mint tea item
- Living Armor Upgrade for Mana attunement
- Initial version of Living Mage Armor
