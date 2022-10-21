# BalloonsPlus - Add balloons on your minecraft server
***Forked from :*** [SharkZekrom - BallonsPlus](https://github.com/SharkZekrom/BallonsPlus)

### Commands and permissions

|        Command        |          Permission          | Default |
|:---------------------:|:----------------------------:|---------|
| /balloonsplus reload  |     balloonsplus.reload      | false   |
|  /balloonsplus equip  |      balloonsplus.equip      | true    |
| /balloonsplus unequip |     balloonsplus.unequip     | true    |
|  | balloonsplus.balloon.default | true    |

### Balloons and balloon permission
You can add new balloons from the Settings.yml file. <br>
Add a new balloon in the Balloons section:

```
Balloons:
  Red_Balloon:                        #Balloon name
    isDefault: true                   #If false player need permission to use this balloon
    key: "<head_value>"               #Insert head_value. Get it on https://minecraft-heads.com/
```
At this moment you can add ballons only editing Settings.yml file.<br>
Use **/balloonsplus reload** command if you want to add balloons without reload 
or restart your minecraft server.

Permission of balloons with 'isDefault' flag set to true:
```
balloonsplus.balloon.default
```
Permission of balloons with 'isDefault' set to false:
```
balloonsplus.balloon.<balloon_name>
```
Example:
```
Balloons:
  Red_Balloon:
    isDefault: true
    key: "<head_value>"
  Pink_Balloon:
    isDefault: false
    key: "<head_value>"
```

To equip **Red_Balloon** players need : _balloonsplus.balloon.default_<br>
To equip **Pink_Balloon** players need: _balloonsplus.balloon.Pink_Balloon_



### Planned features (maybe)
*I don't know if and when this features will be added to the plugin.*
- Vault support
- Allow the balloons to be attached to the fences
- Balloons with particles
- equip <player_name> command (admin command).
- Allow players head as balloon
