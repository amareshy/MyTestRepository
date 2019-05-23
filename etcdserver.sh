ETCD_VER=v3.3.12

# choose either URL
GOOGLE_URL=https://storage.googleapis.com/etcd
GITHUB_URL=https://github.com/etcd-io/etcd/releases/download
DOWNLOAD_URL=${GOOGLE_URL}
LOCAL_DOWNLOAD_PATH=/home/amaresh/user_drive/Study/etcd-server

mkdir ${LOCAL_DOWNLOAD_PATH}
rm -f ${LOCAL_DOWNLOAD_PATH}/etcd-${ETCD_VER}-linux-amd64.tar.gz
rm -rf ${LOCAL_DOWNLOAD_PATH}/etcd-download-test && mkdir -p ${LOCAL_DOWNLOAD_PATH}/etcd-download-test

curl -L ${DOWNLOAD_URL}/${ETCD_VER}/etcd-${ETCD_VER}-linux-amd64.tar.gz -o ${LOCAL_DOWNLOAD_PATH}/etcd-${ETCD_VER}-linux-amd64.tar.gz
tar xzvf ${LOCAL_DOWNLOAD_PATH}/etcd-${ETCD_VER}-linux-amd64.tar.gz -C ${LOCAL_DOWNLOAD_PATH}/etcd-download-test --strip-components=1
# rm -f ${LOCAL_DOWNLOAD_PATH}/etcd-${ETCD_VER}-linux-amd64.tar.gz

${LOCAL_DOWNLOAD_PATH}/etcd-download-test/etcd --version
export ETCDCTL_API=3 ${LOCAL_DOWNLOAD_PATH}/etcd-download-test/etcdctl version
