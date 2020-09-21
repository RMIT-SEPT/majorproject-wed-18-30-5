# FROM node:12
FROM node:10-alpine AS NPM_BUILD
COPY package*.json /app/
COPY src /app/src/
COPY public /app/public/
WORKDIR /app/
RUN npm install
CMD [ "npm", "start" ]