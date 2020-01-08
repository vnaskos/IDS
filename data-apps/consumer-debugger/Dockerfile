FROM node:alpine

WORKDIR /app

ADD package.json /app
RUN npm install

ADD server.js /app

EXPOSE 8081

ENTRYPOINT [ "node", "server.js" ]
