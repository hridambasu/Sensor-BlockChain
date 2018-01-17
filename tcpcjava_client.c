#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <errno.h>
#include <fcntl.h>
#include <stdint.h>
/**
 * Linux C socket
 */
#define MYPORT 8887
/*typedef struct{
    uint8_t lxx;
    int nodeID;
    int updateFlg;
    uint16_t slot;
    uint32_t slotBitmap;
    float BitMap;
} test_stru,*p_stru;*/

typedef struct{
    uint8_t id;
    int type;
    int level;
    int source;
    float value;
} test_stru,*p_stru;

int main(int argc, char *argv[])
    {
    //test_stru test1 = {200,121,127,4,4294967279,5648.525};
    if(argc != 2)
    {
	printf("Args Error. Usage: ./sensor <server_ip>");
	exit(1);
    }
    enum dataType {Usage = 1, TEMPERATURE, PRESSURE, HUMIDITY}dataType;
    enum dataLevel {URGENT = 1, NORMAL, NONURGENT};
    //int type = (enum dataType)type;
    test_stru test1 = {12,Usage,NONURGENT,234,5648.525};
    printf("test_stru size is  %ld\n",sizeof(test_stru));
    p_stru p_test = &test1;
    size_t inlen = sizeof(test_stru);
    char outbuf[1024]={};
    size_t outlen = sizeof(outbuf);
    int brdcFd;
    if((brdcFd=socket(PF_INET,SOCK_DGRAM,0))<0){
        printf("socket fail\n");
        return -1;
    }
    int optval =1;
    setsockopt(brdcFd,SOL_SOCKET,SO_BROADCAST|SO_REUSEADDR,&optval,sizeof(int));
    struct sockaddr_in theirAddr;
    memset(&theirAddr,0,sizeof(struct sockaddr_in));
    theirAddr.sin_family =AF_INET;
    theirAddr.sin_addr.s_addr =inet_addr(argv[1]);
    theirAddr.sin_port = htons(MYPORT);
    int sendBytes;
    while(1){
        if((sendBytes=sendto(brdcFd,p_test,sizeof(test_stru),0,(struct sockaddr *)&theirAddr,sizeof(struct sockaddr)))==-1){
            printf("sendto fail,errno=%d\n",errno);
            return -1;
        }
        printf("sending successfully!!!\n");
        //printf("msg=%s,msgLen=%d,sendBytes=%d\n",msg,(int)strlen(msg),sendBytes);
        sleep(2);
    }
    close(brdcFd);
}
