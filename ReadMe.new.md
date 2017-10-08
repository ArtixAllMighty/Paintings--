# Paintings ++

> _**Q:** Why is this repository called Paintings-- but the mod is called Paintings ++?_<br>
> _**A:** Because you can't have a file or folder with a plus in its name._

Paintings ++ is a mod for Forge Minecraft which replaces the paintings built into Minecraft with new ones. T

Paintings in Minecraft take the form of a single PNG image file that is divided up into standard regions. Each individual painting goes into one of those regions, resulting in a texture file that looks something like this:

![gibea.png](src/main/resources/assets/subaraki/art/gibea.png?raw=true)

Your friendly mod maker recommends pairing Paintings ++ with the [Painting Selection Gui Revamped](https://mods.curse.com/mc-mods/minecraft/252043-painting-selection-gui-revamped) (PSG). PSG presents a menu, whenever you place a painting, from which to choose a which painting to place. Without PSG, placing a painting just gives you a random picture. 

## Resource Pack



```
<root resource folder>
  ├─ assets/
  │   └─ subaraki/
  │       ├─ art/
  │       │   └─ <texture>.png
  │       └─ patterns/ (OPTIONAL) 
  │           └─ <name>.json
  ├─ pack.mcmeta
  └─ pack.png
```

You can find a tutorial on how to build a resource pack on the [official Minecraft Wiki(https://minecraft.gamepedia.com/Tutorials/Loading_a_resource_pack).

## Premade Textures

There are a few PNG files to choose from on the [Paintings-- project Wiki](/ArtixAllMighty/Paintings--/wiki/Art-Files).

## Texture Templates

These templates give you paintings at a resolution of 16&times;16 pixels per block, which is the resolution of vanilla Minecraft textures. Several of the templates come with pictures already added to get you started, but those pictures can be replaced.

To make higher resolution paintings, you'll need to resize the template you're working with.

The Gibea texture is the original picture set included as part of this mod. It contains both the vanilla paintings and some new ones.

| Pattern                                                               | Template                                                       |
| :-------------------------------------------------------------------- | :------------------------------------------------------------- |
| Gibea<br>[gibea.png][gibea_url]<br>16&times;16 blocks<br>88 paintings | [![Gibeaa template image][gibea_url]][gibea_url]               |
| Sphax<br>[sphax.png][sphax_url]<br>16&times;16 blocks<br>50 paintings | [![Sphax template image][sphax_url]][sphax_url]                |
| Tiny<br>[tinypics.png][tinypics_url]<br>32&times;32<br>302<br>        | [![Tiny template image][tinypics_url]][tinypics_url]           |
| Medium<br>[mediumpics.png][mediumpics_url]<br>32&times;32<br>222      | [![Medium template image][mediumpics_url]][mediumpics_url]     |
| Insane*<br>[new\_insane.png][new_insane_url]<br>32&times;32<br>121    | [![New Insane template image][new_insane_url]][new_insane_url] |
| Massive<br>[massive.png][massive_url]<br>63&times;63<br>281           | [![Massive template image][massive_url]][massive_url]          |

[gibea_url]: src/main/resources/assets/subaraki/art/gibea.png
[sphax_url]: src/main/resources/assets/subaraki/art/sphax.png
[tinypics_url]: src/main/resources/assets/subaraki/art/tinypics.png
[mediumpics_url]: src/main/resources/assets/subaraki/art/mediumpics.png
[new_insane_url]: src/main/resources/assets/subaraki/art/new_insane.png
[massive_url]: src/main/resources/assets/subaraki/art/massive.png

\* There are actually two layouts for _Insane_ but the original _insane.png_ was laid out in a confusing and random fashion. The format still works but _new\_insane.png_ is recommended for any new textures. Both versions have exactly the same number and size of images.

### Making Your Own Template

# Free Graphics Editors

If you don't have any graphics editing software, there are a number of free programs on the Internet that can help you out:

* [The GIMP](https://www.gimp.org/) (Windows, MacOS, Linux)
* [Krita](https://krita.org) (Windows, MacOS, Linux)
* [Paint.NET](https://www.getpaint.net/) (Windows only)

# Lists

## Detailed List of Image Sizes

| Size  | # Gibea | # Sphax | # Tiny | # Medium | # Insane | # Massive |
| :---: | :-----: | :-----: | :----: | :------: | :------: | :-------: |
| 1x1   | 24      | 10      | 70     | 46       | 11       | 20        |
| 1x2   | 12      | 4       | 32     | 10       | 11       | 16        |
| 1x3   |         | 3       | 32     | 5        | 6        | 5         |
| 1x4   |         | 1       | 4      |          |          | 3         |
| 2x1   | 36      | 7       | 32     | 22       | 14       | 20        |
| 2x2   | 10      | 12      | 36     | 64       | 18       | 1         |
| 2x3   |         | 1       | 16     | 16       | 4        | 11        |
| 2x4   |         |         | 4      | 6        |          | 8         |
| 3x1   |         | 2       | 32     |          | 6        | 5         |
| 3x2   |         | 1       | 16     | 18       | 2        | 18        |
| 3x3   |         |         | 8      | 9        | 3        | 7         |
| 4x1   |         |         | 8      |          | 8        | 12        |
| 4x2   | 1       | 1       | 4      | 10       | 5        | 18        |
| 4x3   | 2       | 4       | 5      | 8        | 8        | 20        |
| 4x4   | 3       | 3       | 3      | 8        | 10       | 6         |
| 4x6   |         |         |        |          | 4        | 16        |
| 4x8   |         |         |        |          | 2        | 4         |
| 5x5   |         | 1       |        |          | 2        | 7         |
| 8x1   |         |         |        |          | 1        |           |
| 8x4   |         |         |        |          | 4        | 8         |
| 8x6   |         |         |        |          | 1        |           |
| 8x8   |         |         |        |          | 1        | 1         |

## Additional Massive Template Sizes

| Size  | #    | Size  | #    | Size  | #    | Size  | #    |
| :---: | :--- | :---: | :--- | :---: | :--- | :---: | :--- |
| 1x10  | 1    | 4x5   | 1    | 6x3   | 6    | 8x3   | 1    |
| 3x4   | 7    | 5x1   | 5    | 6x4   | 10   | 8x12  | 1    |
| 3x5   | 5    | 5x7   | 5    | 6x9   | 3    | 9x6   | 4    |
| 3x6   | 4    | 5x8   | 5    | 7x5   | 4    | 10x1  | 2    |
| 3x7   | 2    | 5x10  | 2    | 8x2   | 5    | 10x5  | 5    |

## Symbols in Default Templates

|       | 1     | 2     | 3     | 4     | 5     | 6     | 7     | 8     | 9     | 10    |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| 1     | A     | F     | J     | Q     | X     |       |       | g     |       | o     |
| 2     | B     | G     | K     | R     |       |       |       | h     |       |       |
| 3     | C     | H     | L     | S     |       | c     |       | i     |       |       |
| 4     | D     | I     | M     | T     |       | d     |       | j     |       |       |
| 5     |       |       | N     | U     | Y     |       | f     |       |       | p     |
| 6     |       |       | O     | V     |       |       |       | k     | n     |       |
| 7     |       |       | P     |       | Z     |       |       |       |       |       |
| 8     |       |       |       | W     | a     |       |       | l     |       |       |
| 9     |       |       |       |       |       | e     |       |       |       |       |
| 10    | E     |       |       |       | b     |       |       |       |       |       |
| 12    |       |       |       |       |       |       |       | m     |       |       |

