FROM python:3.10
WORKDIR /DiscordBot
COPY requirements.txt /DiscordBot/
RUN pip install -r requirements.txt
COPY . /DiscordBot
CMD python3 bot.py