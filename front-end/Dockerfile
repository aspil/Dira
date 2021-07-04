FROM node as react-build
RUN mkdir /app
WORKDIR /app
COPY . /app
RUN yarn
RUN yarn build

FROM nginx:alpine
COPY --from=react-build /app/build /usr/share/nginx/html
EXPOSE 80
