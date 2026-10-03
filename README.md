## 🌐简体中文  [English](https://github.com/Xing-C/Classic-and-simple-status-bars/blob/main/README-en.md)

## [CSSB]经典且简易的状态栏 Classic and simple status bars

![logo](/src/main/resources/image.png)

### 本模组基于 [SimpleHealthBar](https://github.com/Lanfix8/SimpleHealthBar-Forge) 开源修改，已获作者 [@Lanfix8](https://github.com/Lanfix8) 允许发布。

> 每个 Minecraft 版本都是**独立的模组**，互不通用。本仓库（`1.21.1` 分支）与本文档对应 **Minecraft 1.21.1 / NeoForge**。

### 🌈功能特点

#### 基础显示

- 可定义的功能开关、文本颜色格式：几乎所有文本颜色都能单独调整；
- 根据 DeBuff 进行纹理变化：凋零、中毒、冻结、饥饿；
- 通过数字显示生命值，可以显示大于 20 点的生命值；
- 可以显示伤害吸收量；
- 可以显示饥饿值、最大值以及消耗（可配置）；
- 可以显示饱和度；
- 可以在各种状态下显示剩余氧气百分比；
- 显示护甲值与护甲韧性；
- 在骑马或别的东东时，显示坐骑生命值信息（当前值与最大值颜色独立可配置）。

#### 联动兼容

- 口渴 / 意志坚定 / 稳态：显示其水分值；
- 吸血鬼：显示饮血栏，玩家被感染后接管饱食度显示；
- 跑酷（ParCool）：体力以新样式展示（渲染类型不是 Normal 时），可单独开关与配色；
- 通用机械（Mekanism）：显示装备能量条，可单独开关与配色；
- 奇异饰品（Artifacts）：显示火烈鸟泳圈的飞行时间与破裂状态；
- 护甲显示上限突破（Overloaded Armor Bar）：避免护甲条重复显示；
- 传说生存（Legendary Survival Overhaul）：接替水分条与寒冷饥饿条，支持破碎心 / 护盾、干渴 / 脱水状态图标；
- 苹果皮（AppleSkin）：取消其在饱食度上的叠加预览，避免与自绘饱食度、饱和度重复；
- 以上联动模组均为**可选依赖**：没安装也能正常进入游戏。

#### 仅 1.20.x 分支

- 起源模组的职业能力条、脱水模组的饥渴值、超级饱和度模组的额外饱食、蔚蓝皓空模组的饰品吸收、Feathers 模组的羽毛耐力、Scaling Health 模组的生命栏屏蔽。

> 「仅 1.20.x」的联动在 1.21.1 下没有可用的 NeoForge 版本，本分支未包含，它们仍在 1.20.x 分支中维护。

### 🪶支持版本

每个 Minecraft 版本都是一个独立的模组，请下载与你的游戏版本对应的那一个：

- **Minecraft 1.21.1** —— NeoForge 加载器（本分支 `1.21.1`，即本文档）；
- **Minecraft 1.20 - 1.20.1** —— Forge / NeoForged 加载器（`main` 分支）；
- **Minecraft 1.20 - 1.20.2** —— Fabric 加载器（`1.20.x-Fabric` 分支已停止更新）；
- 计划支持未来更新的版本。

#### ⏬下载

- [Modrinth](https://modrinth.com/mod/cssb)
- [MC百科-文件下载](https://www.mcmod.cn/class/12121.html)
- [CurseForge](https://curseforge.com/minecraft/mc-mods/classic-and-simple-status-bars)

### 📋更新日志

#### v26.10.03.1

**🎉 更新至 Minecraft 1.21.1 / NeoForge**

- 状态栏渲染改为适配 1.21.1 新的 GUI 层机制；
- 联动兼容改为按「界面层 ID」判断并取消对方的渲染；
- 新增**传说生存（Legendary Survival Overhaul）**兼容：接替水分条与寒冷饥饿条，支持破碎心 / 护盾、干渴 / 脱水状态图标。

#### v26.09.30.2

**🆕 新增兼容**

- 护甲显示上限突破（Overloaded Armor Bar）：不再和它重复画出第二条护甲条，只保留本模组的样式；
- Ok Zoomer（缩放模组）：可以正常共存，互不干扰。

**🐛 问题修复**

- 装了通用机械（Mekanism）时，装备能量图标会显示成黑紫方块，现已正常显示；
- 生命值带有「伤害吸收」时，血条会被画得过长、右端多出一截，现已修正。

**🔧 体验优化**

- 兼容模组改为真正的可选：没有安装对应模组的玩家也能正常进入游戏，不会报错、不会闪退；
- 移除已失效的灾变模组兼容（灾变 3.x 自己已经删掉了沙暴计时条）。

### 🖼️展示：

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

# 😀你收到了一个祝福，祝你每天都开开心心！
