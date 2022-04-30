# SweetUtilities

> /rank <Player> - Opens the Promotion/Demotion Menu - **sweetutilities.rank** || **sweetutilities.rank.admin**
> /quote <Section> - Sends a Quote - **sweetutilities.quote**

__greetings.yml__
```yml
# My plugins support HEX, You can do: &#hex
# Variables:
# - %player%
# - %displayname%
greetings:
  join:
    - "&fPlayer &6%player% &fhas joined the server, say hi!"
    - "&fLooks like &6%player% &ftook the good route!"
  leave:
    - "&fPlayer &6%player% &fhas left the server, goodbye!"
    - "&fLooks like &6%player% &ftook a wrong turn..."
```
__quotes.yml__
```yml
quotes:
  enabled: false
  format: " · &8(&4&lQUOTE&8) &7'&f%quote%&7'"
  # Supports infinite options, and infinite quotes underneath.
  options:
    owner:
      - "Insert owner quote"
    admin:
      - "Insert admin quote"
```
__ranks.yml__
```yml
track: "staff"
```
