BungeeCord - BarelyAuthenticated edition
==========
A simple fork that has fallback measures on redis for when mojang craps out. Build for BlockParty by barelyalive. It works by caching gameprofiles based on player's ip address and name, when the Mojang api is detected to be unstable, it'll fall back on its own cache to accept players who joined before. New players won't be able to join until mojang is back up.

Config options:
 - `mojangFailsBeforeFallback` (int) The amount of times mojang is allowed to fail before falling back to REDIS.
 - `mojangSuccessesBeforeOnline` (int) The amount of times mojang should succeed before we switch back to their session server
 - `mojangAttemptInterval` (int) is the join interval for re-attempts to mojang. Defaults to 20, so for every 20 NEW players that try to join, one will get an actual mojang attemp. This can impact performance in scenarios where you have an absolute ass load of players trying to login at once so keep it as high as possible.
 - `preferRedisAuthentication` (boolean) If the redis is prefered for authentication. If so, it'll always check redis first for a cached profile even when the server is online, and possibly only fall back to mojang for unknown players (given that mojangs state is online). This will greatly reduce the amount of requests to mojang but will also load the redis heavily with storage and requests. **WARNING: Defaults to TRUE!**
 - `redisIpStorageExpiery` (int) The amount of seconds for which to cache player profiles (in seconds), defaults to 12 hours because thats the anonymous GDPR limit
 - `redisPort` (int) The port to your magical redis server
 - `redisHost` (String) the hostname for your magical redis server
 - `redisPassword` (String) your redis password. Set to "none" to connect without authentication (which is the default)
 - `redisDatabase` (int) The database index to use, defaults to 15
 - `redisUsesSSL` (boolean) If redis should connect over TLS with your redis server. Defaults to false. I don't know why you would want to but you can.
 
 # Help
 If this came in handy, please consider donating at http://donate.craftmend.com/. If you need any help, just contact me.