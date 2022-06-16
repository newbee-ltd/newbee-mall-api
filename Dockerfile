# 指定jdk环境版本，基于java8创建镜像
FROM java:8

#作者
MAINTAINER luna <luna-nov@163.com>

#用于在镜像容器中执行命令

#声明一个挂载点，容器内此路径会对应宿主机的某个文件夹
VOLUME ["/root/newbee","/usr/local/newbee"]

#应用构建成功后的jar文件被复制到镜像内，名字也改成了app.jar
ADD target/newbee-mall-api-3.0.0-SNAPSHOT.jar /newbee-mall-api.jar

#启动容器时的进程
ENTRYPOINT ["java","-Duser.timezone=GMT+8","-jar","/newbee-mall-api.jar"]

#暴露端口
EXPOSE 28019

