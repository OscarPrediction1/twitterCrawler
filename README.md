# twitterCrawler
## Info
Collecting tweets with the streamingAPI. Keywords are specified in the search.txt.
## Output
For each tweet there is a JSONObject as follows:
```javascript
{
  "_id": ObjectId("56311363ed40903a721bb9f1"),
  "content": "\"@alphabetsuccess: Don't let the noise of other people's opinions drown out your own inner voice - Steve Jobs #quote via @JeffSheehan\"",
  "timestamp": "2015-10-28 19:23:52.836",
  "id": NumberLong(659435428538425345),
  "favoritecount": 0,
  "username": "MaureenMeiky",
  "location": null,
  "retweetcount": 0
}
```
## Build
run `mvn install` and use the ...jar-with-dependencies.jar; or take the bettingOddsCrawler-0.0.1.jar from the repository if you don't want to build it yourself.
## Run
`java -jar twitterCrawler-0.0.1.jar [host] [port] [userName] [password] [database] [pathToSearch.txt]`

e.g. `java -jar bettingOddsCrawler-0.0.1.jar 23.102.28.222 27017 oscar 123456 oscar search.txt`

