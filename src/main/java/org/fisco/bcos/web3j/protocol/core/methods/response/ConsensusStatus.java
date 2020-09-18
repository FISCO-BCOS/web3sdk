package org.fisco.bcos.web3j.protocol.core.methods.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.fisco.bcos.web3j.protocol.ObjectMapperFactory;
import org.fisco.bcos.web3j.protocol.core.Response;

/** getConsensusStatus */
public class ConsensusStatus extends Response<ConsensusStatus.ConsensusInfo> {
    @Override
    @JsonDeserialize(using = ConsensusStatusDeserializer.class)
    public void setResult(ConsensusStatus.ConsensusInfo result) {
        super.setResult(result);
    }

    public ConsensusInfo getConsensusStatus() {
        return getResult();
    }

    public static class ViewInfo {
        private String nodeId;
        private String view;

        public String getNodeId() {
            return nodeId;
        }

        public void setNodeId(String nodeId) {
            this.nodeId = nodeId;
        }

        public String getView() {
            return view;
        }

        public void setView(String view) {
            this.view = view;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ViewInfo viewInfo = (ViewInfo) o;
            return Objects.equals(nodeId, viewInfo.nodeId) && Objects.equals(view, viewInfo.view);
        }

        @Override
        public int hashCode() {
            return Objects.hash(nodeId, view);
        }

        @Override
        public String toString() {
            return "ViewInfo{" + "nodeId='" + nodeId + '\'' + ", view='" + view + '\'' + '}';
        }
    }

    public static class BasicConsensusInfo {
        private String nodeNum;

        @JsonProperty("node_index")
        private String nodeIndex;

        @JsonProperty("node index")
        private String raftNodeIndex;

        @JsonProperty("max_faulty_leader")
        private String maxFaultyNodeNum;

        @JsonProperty("sealer.")
        private List<String> sealerList;

        private String consensusedBlockNumber;
        private String highestblockNumber;
        private String groupId;
        private String protocolId;
        private String accountType;
        private String cfgErr;
        private String omitEmptyBlock;
        private String nodeId;
        private String allowFutureBlocks;
        private String connectedNodes;
        private String currentView;
        private String toView;
        private String leaderFailed;
        private String highestblockHash;
        private String leaderId;
        private String leaderIdx;

        public String getRaftNodeIndex() {
            return raftNodeIndex;
        }

        public void setRaftNodeIndex(String raftNodeIndex) {
            this.raftNodeIndex = raftNodeIndex;
        }

        public String getLeaderId() {
            return leaderId;
        }

        public void setLeaderId(String leaderId) {
            this.leaderId = leaderId;
        }

        public String getLeaderIdx() {
            return leaderIdx;
        }

        public void setLeaderIdx(String leaderIdx) {
            this.leaderIdx = leaderIdx;
        }

        public String getNodeIndex() {
            return nodeIndex;
        }

        public void setNodeIndex(String nodeIndex) {
            this.nodeIndex = nodeIndex;
        }

        public String getHighestblockHash() {
            return highestblockHash;
        }

        public void setHighestblockHash(String highestblockHash) {
            this.highestblockHash = highestblockHash;
        }

        public String getNodeNum() {
            return nodeNum;
        }

        public void setNodeNum(String nodeNum) {
            this.nodeNum = nodeNum;
        }

        public String getMaxFaultyNodeNum() {
            return maxFaultyNodeNum;
        }

        public void setMaxFaultyNodeNum(String maxFaultyNodeNum) {
            this.maxFaultyNodeNum = maxFaultyNodeNum;
        }

        public List<String> getSealerList() {
            return sealerList;
        }

        public void setSealerList(List<String> sealerList) {
            this.sealerList = sealerList;
        }

        public String getConsensusedBlockNumber() {
            return consensusedBlockNumber;
        }

        public void setConsensusedBlockNumber(String consensusedBlockNumber) {
            this.consensusedBlockNumber = consensusedBlockNumber;
        }

        public String getHighestblockNumber() {
            return highestblockNumber;
        }

        public void setHighestblockNumber(String highestblockNumber) {
            this.highestblockNumber = highestblockNumber;
        }

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public String getProtocolId() {
            return protocolId;
        }

        public void setProtocolId(String protocolId) {
            this.protocolId = protocolId;
        }

        public String getAccountType() {
            return accountType;
        }

        public void setAccountType(String accountType) {
            this.accountType = accountType;
        }

        public String getCfgErr() {
            return cfgErr;
        }

        public void setCfgErr(String cfgErr) {
            this.cfgErr = cfgErr;
        }

        public String getOmitEmptyBlock() {
            return omitEmptyBlock;
        }

        public void setOmitEmptyBlock(String omitEmptyBlock) {
            this.omitEmptyBlock = omitEmptyBlock;
        }

        public String getNodeId() {
            return nodeId;
        }

        public void setNodeId(String nodeId) {
            this.nodeId = nodeId;
        }

        public String getAllowFutureBlocks() {
            return allowFutureBlocks;
        }

        public void setAllowFutureBlocks(String allowFutureBlocks) {
            this.allowFutureBlocks = allowFutureBlocks;
        }

        public String getConnectedNodes() {
            return connectedNodes;
        }

        public void setConnectedNodes(String connectedNodes) {
            this.connectedNodes = connectedNodes;
        }

        public String getCurrentView() {
            return currentView;
        }

        public void setCurrentView(String currentView) {
            this.currentView = currentView;
        }

        public String getToView() {
            return toView;
        }

        public void setToView(String toView) {
            this.toView = toView;
        }

        public String getLeaderFailed() {
            return leaderFailed;
        }

        public void setLeaderFailed(String leaderFailed) {
            this.leaderFailed = leaderFailed;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            BasicConsensusInfo that = (BasicConsensusInfo) o;
            return Objects.equals(nodeNum, that.nodeNum)
                    && Objects.equals(nodeIndex, that.nodeIndex)
                    && Objects.equals(maxFaultyNodeNum, that.maxFaultyNodeNum)
                    && Objects.equals(sealerList, that.sealerList)
                    && Objects.equals(consensusedBlockNumber, that.consensusedBlockNumber)
                    && Objects.equals(highestblockNumber, that.highestblockNumber)
                    && Objects.equals(groupId, that.groupId)
                    && Objects.equals(protocolId, that.protocolId)
                    && Objects.equals(accountType, that.accountType)
                    && Objects.equals(cfgErr, that.cfgErr)
                    && Objects.equals(omitEmptyBlock, that.omitEmptyBlock)
                    && Objects.equals(nodeId, that.nodeId)
                    && Objects.equals(allowFutureBlocks, that.allowFutureBlocks)
                    && Objects.equals(connectedNodes, that.connectedNodes)
                    && Objects.equals(currentView, that.currentView)
                    && Objects.equals(toView, that.toView)
                    && Objects.equals(leaderFailed, that.leaderFailed)
                    && Objects.equals(highestblockHash, that.highestblockHash);
        }

        @Override
        public int hashCode() {
            return Objects.hash(
                    nodeNum,
                    nodeIndex,
                    maxFaultyNodeNum,
                    sealerList,
                    consensusedBlockNumber,
                    highestblockNumber,
                    groupId,
                    protocolId,
                    accountType,
                    cfgErr,
                    omitEmptyBlock,
                    nodeId,
                    allowFutureBlocks,
                    connectedNodes,
                    currentView,
                    toView,
                    leaderFailed,
                    highestblockHash);
        }

        @Override
        public String toString() {
            return "BasicConsensusInfo{"
                    + "nodeNum='"
                    + nodeNum
                    + '\''
                    + ", nodeIndex='"
                    + nodeIndex
                    + '\''
                    + ", maxFaultyNodeNum='"
                    + maxFaultyNodeNum
                    + '\''
                    + ", sealerList="
                    + sealerList
                    + ", consensusedBlockNumber='"
                    + consensusedBlockNumber
                    + '\''
                    + ", highestblockNumber='"
                    + highestblockNumber
                    + '\''
                    + ", groupId='"
                    + groupId
                    + '\''
                    + ", protocolId='"
                    + protocolId
                    + '\''
                    + ", accountType='"
                    + accountType
                    + '\''
                    + ", cfgErr='"
                    + cfgErr
                    + '\''
                    + ", omitEmptyBlock='"
                    + omitEmptyBlock
                    + '\''
                    + ", nodeId='"
                    + nodeId
                    + '\''
                    + ", allowFutureBlocks='"
                    + allowFutureBlocks
                    + '\''
                    + ", connectedNodes='"
                    + connectedNodes
                    + '\''
                    + ", currentView='"
                    + currentView
                    + '\''
                    + ", toView='"
                    + toView
                    + '\''
                    + ", leaderFailed='"
                    + leaderFailed
                    + '\''
                    + ", highestblockHash='"
                    + highestblockHash
                    + '\''
                    + '}';
        }
    }

    public static class ConsensusInfo {
        private BasicConsensusInfo baseConsensusInfo;
        private List<ViewInfo> viewInfos;

        public ConsensusInfo() {}

        public ConsensusInfo(BasicConsensusInfo baseConsensusInfo, List<ViewInfo> viewInfos) {
            this.baseConsensusInfo = baseConsensusInfo;
            this.viewInfos = viewInfos;
        }

        public BasicConsensusInfo getBaseConsensusInfo() {
            return baseConsensusInfo;
        }

        public void setBaseConsensusInfo(BasicConsensusInfo baseConsensusInfo) {
            this.baseConsensusInfo = baseConsensusInfo;
        }

        public List<ViewInfo> getViewInfos() {
            return viewInfos;
        }

        public void setViewInfos(List<ViewInfo> viewInfos) {
            this.viewInfos = viewInfos;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ConsensusInfo that = (ConsensusInfo) o;
            return Objects.equals(baseConsensusInfo, that.baseConsensusInfo)
                    && Objects.equals(viewInfos, that.viewInfos);
        }

        @Override
        public int hashCode() {
            return Objects.hash(baseConsensusInfo, viewInfos);
        }

        @Override
        public String toString() {
            return "ConsensusInfo{"
                    + "baseConsensusInfo="
                    + baseConsensusInfo
                    + ", viewInfos="
                    + viewInfos
                    + '}';
        }
    }

    public static class ConsensusStatusDeserializer extends JsonDeserializer<ConsensusInfo> {
        private ObjectMapper objecMapper = ObjectMapperFactory.getObjectMapper();

        @Override
        public ConsensusInfo deserialize(
                JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException {
            JsonNode node = jsonParser.getCodec().readTree(jsonParser);
            BasicConsensusInfo baseConsensusInfo = null;
            List<ViewInfo> viewInfos = null;
            if (node.size() > 0) {
                baseConsensusInfo =
                        objecMapper.readValue(node.get(0).toString(), BasicConsensusInfo.class);
                Integer sealersNum = Integer.valueOf(baseConsensusInfo.getNodeNum());
                baseConsensusInfo.setSealerList(new ArrayList<String>(sealersNum));
                // parse sealerList
                for (Integer i = 0; i < sealersNum; i++) {
                    String key = "sealer." + String.valueOf(i);
                    if (node.get(0).has(key)) {
                        baseConsensusInfo.getSealerList().add(i, node.get(0).get(key).asText());
                    }
                }
            }
            if (node.size() > 1) {
                viewInfos =
                        objecMapper.readValue(
                                node.get(1).toString(), new TypeReference<List<ViewInfo>>() {});
            }
            return new ConsensusInfo(baseConsensusInfo, viewInfos);
        }
    }
}
