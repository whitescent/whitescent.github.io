---
title: 三阶魔方单次 PB 解法
slug: cube-pb
date: 2022-03-06 00:00:00+0000
categories:
    - 日常
---


Result: 7.18s

打乱：D2 F2 D2 L R2 B2 L B2 F2 R' U' F2 R' D F R2 U' R B

```
X2 // inspection 白底蓝前
U' F2 u R y' U R' F R D // 十字
R U' R' y' U' L' U L  // 1st pair
U' R U R' U' y R' U' R // 2nd pair
U' F U' F' // 3rd pair
(U R U' R') x 3 // 4th pair

U' r U R' U' M U R U' R' // OLL

ETM:54
ETPS:7.51
```