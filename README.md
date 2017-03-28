# ChatBot
A simple chat bot to parse user input to identify mentions, emoticons within the input. The parsing of the input string and identification of the special character sequence is driven by rules.

For now, parsing is done for 4 rules -
1. Mentions - ex @john every word staring with an @ sign will be extracted
2. Emoticons - ex (success) every word in parenthesis will be extracted
3. Urls - ex https://google.com every url will be extracted
4. Hash - ex #done every word staring with an # sign will be extracted

Parser will respond with json having list of mentions, emoticons, urls and hash.

![Chatbot Screenshot 1](https://www.dropbox.com/s/niucfyy5fzupfti/chatbot_parser1.png?dl=1)

![Chatbot screenshot 2](https://www.dropbox.com/s/071pp2ry7mm0a16/chatbot_parser_2.png?dl=1)

![Chatbot screnshot 3](https://www.dropbox.com/s/kmq44gf7yt45334/chatbot_parser_3.png?dl=1)



