#Build image
FROM node:18-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build --prod

#Serve using Nginx
FROM nginx:1.23-alpine
COPY --from=build /app/dist/* /usr/share/nginx/html
# ✅ Copy custom reverse proxy config
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80 
