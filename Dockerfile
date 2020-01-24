FROM hseeberger/scala-sbt:8u222_1.3.2_2.13.1
WORKDIR /scrabble
ADD . /scrabble
CMD sbt run