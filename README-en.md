## 🌐[简体中文](https://github.com/Xing-C/Classic-and-simple-status-bars)  English (No Good, Help Me)

## [CSSB]Classic and simple status bars

![logo](/src/main/resources/image.png)

### This mod is based on the open-source [SimpleHealthBar](https://github.com/Lanfix8/SimpleHealthBar-Forge) with modifications, and is released with the permission of its author [@Lanfix8](https://github.com/Lanfix8).

> Each Minecraft version is a **separate mod**, and they are not interchangeable. This repository (the `1.21.1` branch) and this document cover **Minecraft 1.21.1 / NeoForge**.

### 🌈Features

#### Basic Display

- Configurable function switches and text colour formatting: almost every text colour can be set individually;
- Texture changes based on debuffs: withering, poisoning, freezing and starvation;
- Displays health as a number, and can show health above 20 points;
- Displays absorption;
- Displays hunger, its maximum and its exhaustion (configurable);
- Displays saturation;
- Displays the remaining air percentage in all kinds of situations;
- Displays armor value and armor toughness;
- Displays mount health when riding a horse or anything else (the current and max values are coloured independently).

#### Mod Integrations

- Thirst / Tough As Nails / Homeostatic: displays their hydration value;
- Vampirism: displays the blood-drinking bar and takes over the hunger display once the player is infected;
- ParCool: stamina shown in a new way (when the render type is not Normal), with its own switch and colours;
- Mekanism: displays the equipment energy bar, with its own switch and colour;
- Artifacts: displays the flight time and popped state of the Helium Flamingo;
- Overloaded Armor Bar: avoids a duplicated armor bar;
- Legendary Survival Overhaul: takes over its thirst and cold-hunger bars, and supports the broken heart / shield and the Thirst / Heat Thirst icons;
- All of the above are **optional dependencies**: the game starts normally without them.

#### 1.20.x branch only

- The Origins class ability bar, the Dehydration thirst value, the Super Saturation extra saturation, the Blue Skies accessory absorption, the Feathers endurance system and the Scaling Health bar suppression.

> Integrations marked "1.20.x branch only" have no available NeoForge build for 1.21.1 and are not part of this branch; they are still maintained in the 1.20.x branch.

### 🪶Supported Versions

Each Minecraft version is a separate mod — please download the one that matches your game version:

- **Minecraft 1.21.1** — NeoForge loader (this branch, `1.21.1`, this document);
- **Minecraft 1.20 - 1.20.1** — Forge / NeoForged loaders (the `main` branch);
- **Minecraft 1.20 - 1.20.2** — Fabric loader (the `1.20.x-Fabric` branch, discontinued);
- Plans to support future versions.

#### ⏬Download

- [Modrinth](https://modrinth.com/mod/cssb)
- [MCMOD](https://www.mcmod.cn/class/12121.html)
- [CurseForge](https://curseforge.com/minecraft/mc-mods/classic-and-simple-status-bars)

### 📋Changelog

#### v26.10.03.1

**🎉 Updated to Minecraft 1.21.1 / NeoForge**

- Status bar rendering now adapts to the new GUI layer system of 1.21.1;
- Integrations now identify and cancel the other mod's rendering by its GUI layer id;
- Added **Legendary Survival Overhaul** support: takes over its thirst and cold-hunger bars, and supports the broken heart / shield and the Thirst / Heat Thirst icons.

#### v26.09.30.2

**🆕 New Compatibility**

- Overloaded Armor Bar: its armor bar is no longer drawn a second time — only this mod's style remains;
- Ok Zoomer: works side by side, no more conflicts.

**🐛 Fixes**

- Fixed the Mekanism equipment energy icon showing as a black box with a purple stripe at the bottom — it now displays correctly;
- Fixed the health bar being drawn too long, with an extra segment on the right, when damage absorption is active.

**🔧 Improvements**

- Optional mod support is now truly optional: players without those mods installed can still start the game normally — no errors, no crashes;
- Removed the outdated L_Ender's Cataclysm integration (Cataclysm 3.x removed that sandstorm timer HUD itself).

### 🖼️Showcase:

![0](/Textures/in/0.png)

![1](/Textures/in/1.png)

![2](/Textures/in/2.png)

![3](/Textures/in/3.png)

![4](/Textures/in/4.png)

![5](/Textures/in/5.png)

![6](/Textures/in/6.png)

![7](/Textures/in/7.png)

![8](/Textures/in/8.png)

![9](/Textures/in/9.png)

# 😀You received a blessing — wishing you a wonderful day!
