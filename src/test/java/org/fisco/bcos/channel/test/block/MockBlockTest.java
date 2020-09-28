package org.fisco.bcos.channel.test.block;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.math.BigInteger;
import org.fisco.bcos.web3j.crypto.EncryptType;
import org.fisco.bcos.web3j.crypto.HashInterface;
import org.fisco.bcos.web3j.crypto.SHA3Digest;
import org.fisco.bcos.web3j.crypto.gm.sm3.SM3Digest;
import org.fisco.bcos.web3j.protocol.ObjectMapperFactory;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.Web3jService;
import org.fisco.bcos.web3j.protocol.core.DefaultBlockParameter;
import org.fisco.bcos.web3j.protocol.core.Request;
import org.fisco.bcos.web3j.protocol.core.methods.response.BcosBlock;
import org.fisco.bcos.web3j.protocol.core.methods.response.BcosTransaction;
import org.junit.Assert;
import org.junit.Test;

public class MockBlockTest {

    private Web3jService web3jService = mock(Web3jService.class);
    private ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
    private final String rawResponse =
            "{\"id\":0,\"jsonrpc\":\"2.0\",\"result\":{\"extraData\":[],\"gasLimit\":\"0x0\",\"gasUsed\":\"0x0\",\"hash\":\"0xb1b1612e3d2e6571304e53136002b6c79ae53fcfa207dbe350b61c4a6d0f157f\",\"logsBloom\":\"0x00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000\",\"number\":\"0x1\",\"parentHash\":\"0xe325463b4ed6746dfc4a127e979fd6b95c7d32c754a21e7afbcb8f16b5d0880c\",\"sealer\":\"0x0\",\"stateRoot\":\"0x06ca72b2a5d14f8497412150ecc2d3744c85c26c43a639ba73879f5106ac64d0\",\"timestamp\":\"0x1684a53a6fb\",\"transactions\":[{\"blockHash\":\"0xb1b1612e3d2e6571304e53136002b6c79ae53fcfa207dbe350b61c4a6d0f157f\",\"blockNumber\":\"0x1\",\"from\":\"0x148947262ec5e21739fe3a931c29e8b84ee34a0f\",\"gas\":\"0x11e1a300\",\"gasPrice\":\"0x11e1a300\",\"hash\":\"0xcf9de56878da55b2fb4156bc6268e65d519b87d2b8a320c9f08a966651aa44b1\",\"input\":\"0x60606040525b6001600060005060000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908302179055506402540be4006000600050600101600050819055506002600260005060000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff0219169083021790555060006002600050600101600050819055505b6104168061009e6000396000f360606040526000357c01000000000000000000000000000000000000000000000000000000009004806366c99139146100475780636d4ce63c1461006457610042565b610002565b3461000257610062600480803590602001909190505061008c565b005b346100025761007660048050506103fe565b6040518082815260200191505060405180910390f35b80600060005060010160005054036000600050600101600050819055508060026000506001016000828282505401925050819055507fb797d73164cc7b1c119ca7507c18ac67eac964ca7eed3b0fbdd4e63caab2ca65816040518082815260200191505060405180910390a16004600050805480600101828181548183558181151161020c5760040281600402836000526020600020918201910161020b9190610131565b8082111561020757600060008201600050805460018160011615610100020316600290046000825580601f1061016757506101a4565b601f0160209004906000526020600020908101906101a39190610185565b8082111561019f5760008181506000905550600101610185565b5090565b5b506001820160006101000a81549073ffffffffffffffffffffffffffffffffffffffff02191690556002820160006101000a81549073ffffffffffffffffffffffffffffffffffffffff0219169055600382016000506000905550600401610131565b5090565b5b5050509190906000526020600020906004020160005b608060405190810160405280604060405190810160405280600881526020017f32303137303431330000000000000000000000000000000000000000000000008152602001508152602001600060005060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff168152602001600260005060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681526020018581526020015090919091506000820151816000016000509080519060200190828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061032a57805160ff191683800117855561035b565b8280016001018555821561035b579182015b8281111561035a57825182600050559160200191906001019061033c565b5b5090506103869190610368565b808211156103825760008181506000905550600101610368565b5090565b505060208201518160010160006101000a81548173ffffffffffffffffffffffffffffffffffffffff0219169083021790555060408201518160020160006101000a81548173ffffffffffffffffffffffffffffffffffffffff02191690830217905550606082015181600301600050555050505b50565b60006002600050600101600050549050610413565b9056ef\",\"nonce\":\"0x3df8ad22eff71f4a835e6dd61c6f60b71aded81a86fc4771b5ddba5b48256e0\",\"to\":null,\"transactionIndex\":\"0x0\",\"value\":\"0x0\"}],\"transactionsRoot\":\"0x0aa79ed38e5ea8ff8828f379fa671b0f018b3f38f4e2a4f36062bc35bd55b8ff\"}}\n";

    public MockBlockTest() throws IOException {}

    @Test
    public void getBlockNumber() throws IOException {

        BcosBlock block = objectMapper.readValue(rawResponse, BcosBlock.class);
        block.setRawResponse(rawResponse);

        Web3j web3j = Web3j.build(web3jService);
        when(web3jService.send(any(Request.class), eq(BcosBlock.class))).thenReturn(block);

        BcosBlock mockBlocks =
                web3j.getBlockByNumber(DefaultBlockParameter.valueOf(new BigInteger("1")), true)
                        .send();
        BcosBlock.Block mockBlock = mockBlocks.getBlock();
        assertEquals(mockBlock.getNonce(), new BigInteger("0"));
        assertTrue(mockBlock.getNumber().intValue() == 1);
    }

    @Test
    public void testSMGetTransactionAndCalculateHash() throws IOException {
        String transactionString =
                "{\n"
                        + "  \"id\": 1,\n"
                        + "  \"jsonrpc\": \"2.0\",\n"
                        + "  \"result\": {\n"
                        + "    \"blockHash\": \"0x2ff860cf49b95f721398b78a128617bf62ab03e09002895c4926f7be990615f1\",\n"
                        + "    \"blockLimit\": \"0x38e\",\n"
                        + "    \"blockNumber\": \"0x19b\",\n"
                        + "    \"chainId\": \"0x1\",\n"
                        + "    \"extraData\": \"0x\",\n"
                        + "    \"from\": \"0x37e6cd2081a11c345fac93eaff0ca9ef66f27451\",\n"
                        + "    \"gas\": \"0x419ce0\",\n"
                        + "    \"gasPrice\": \"0x51f4d5c00\",\n"
                        + "    \"groupId\": \"0x1\",\n"
                        + "    \"hash\": \"0x880ee49599e731086d44d268239bce2e36a1b1032329bcd3f194b2e86297caf4\",\n"
                        + "    \"input\": \"0x3590b49f0000000000000000000000000000000000000000000000000000000000000020000000000000000000000000000000000000000000000000000000000000000c48656c6c6f2c20464953434f0000000000000000000000000000000000000000\",\n"
                        + "    \"nonce\": \"0x22cbceaa80e80cf1aa6c719b659601f2ae6ed68d549c537be57b44bc7668405\",\n"
                        + "    \"signature\": {\n"
                        + "      \"r\": \"0xcc108436f41e5ee91491f5e91bd72f1bdc43f6169d2b72bf96c7cf6f32702540\",\n"
                        + "      \"s\": \"0x94eec76fe9d7902a9d328ec169329820914b7720d675c657356fd68f4758108f\",\n"
                        + "      \"signature\": \"0xcc108436f41e5ee91491f5e91bd72f1bdc43f6169d2b72bf96c7cf6f3270254094eec76fe9d7902a9d328ec169329820914b7720d675c657356fd68f4758108f6feaf705e8b16de494b4fec3ec3176e38b1eaa416605f4bb5c141c2a22434580f03b8257b29213bdc059c9b3673a7c3868df55eb1b85c2abc22aae64e4d9cac6\",\n"
                        + "      \"v\": \"0x6feaf705e8b16de494b4fec3ec3176e38b1eaa416605f4bb5c141c2a22434580f03b8257b29213bdc059c9b3673a7c3868df55eb1b85c2abc22aae64e4d9cac6\"\n"
                        + "    },\n"
                        + "    \"to\": \"0x0000000000000000000000000000000000000000\",\n"
                        + "    \"transactionIndex\": \"0x0\",\n"
                        + "    \"value\": \"0x0\"\n"
                        + "  }\n"
                        + "}";

        BcosTransaction bcosTransaction =
                objectMapper.readValue(transactionString.getBytes(), BcosTransaction.class);
        Assert.assertEquals(
                bcosTransaction.getTransaction().get().calculateHash(EncryptType.SM2_TYPE),
                bcosTransaction.getTransaction().get().getHash());
    }

    @Test
    public void testSMGetBlockAndCalculateHash() throws IOException {
        String blockHeaderStr =
                "{\n"
                        + "  \"id\": 1,\n"
                        + "  \"jsonrpc\": \"2.0\",\n"
                        + "  \"result\": {\n"
                        + "    \"dbHash\": \"0x68a77b2364be2f3197bce9ca265a5405ed77904237d8e31dbacfe9e1d3119f77\",\n"
                        + "    \"extraData\": [],\n"
                        + "    \"gasLimit\": \"0x0\",\n"
                        + "    \"gasUsed\": \"0x0\",\n"
                        + "    \"hash\": \"0xc5360efd06024b02340eb2afa283fe022f57791e888f22366b77d6218a247a13\",\n"
                        + "    \"logsBloom\": \"0x00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000\",\n"
                        + "    \"number\": 1,\n"
                        + "    \"parentHash\": \"0x7e1b0fc3efa8026f282bfa994d3a79305542d5ad3ea65b84a8d72b152f15dfb1\",\n"
                        + "    \"receiptsRoot\": \"0xd748b478e6b8f90e049f7a4a9d2b9acf76624baed8c2abe0e868b33cd5e989e5\",\n"
                        + "    \"sealer\": \"0x3\",\n"
                        + "    \"sealerList\": [\n"
                        + "      \"1daca8140ba483b560d1b3b8905ca07f447b305875a4f9c6cb2a826c9315ef10bc87a7e135d0a34f605f3a95ff5d9a8c83f2ac5f070c6fe740400910813110a2\",\n"
                        + "      \"2e6ddeb52fcdb0f0287c8b6bbe407f4a3a52bc1b04ea5b978ab698ac1802eb5db482ec1681b1d1d8d1a5e99143a7cde2b85fe29bbe6538066507a91fc8e5ecc6\",\n"
                        + "      \"4905b78b643c19c03e7b8e6779fca2a3e917baa317e8d2abde6daec543d375ac5052aeda22fda7e174c780e04afd215f965237a809e814369a05bb90b965a6ed\",\n"
                        + "      \"86f731c15ca2f44925fea7f379ca32a55245fb988228305c7625d4a174a186fc4472d4668053c7fe4c8608562cf2fb8fea1ab5ea4c96b9be01949b565ec36c9e\"\n"
                        + "    ],\n"
                        + "    \"signatureList\": [\n"
                        + "      {\n"
                        + "        \"index\": \"0x0\",\n"
                        + "        \"signature\": \"0xa99fcb5298a5dd39644af81b2c3ebd9839ffa9f2cb65c6c8b9f2b84b8804c93cf836cb45059cdefe8767ea922e0141318da7ffcc6d0d9db2b0cfa23638bc86591daca8140ba483b560d1b3b8905ca07f447b305875a4f9c6cb2a826c9315ef10bc87a7e135d0a34f605f3a95ff5d9a8c83f2ac5f070c6fe740400910813110a2\"\n"
                        + "      },\n"
                        + "      {\n"
                        + "        \"index\": \"0x3\",\n"
                        + "        \"signature\": \"0x1addc8032fbca41e31afb429dd2f749653492684fcc7845acb4558d5b09095a311a2d965c2a59133b497cb2553c23f29c6613ca0d312acb4f9fd93df602936f686f731c15ca2f44925fea7f379ca32a55245fb988228305c7625d4a174a186fc4472d4668053c7fe4c8608562cf2fb8fea1ab5ea4c96b9be01949b565ec36c9e\"\n"
                        + "      },\n"
                        + "      {\n"
                        + "        \"index\": \"0x1\",\n"
                        + "        \"signature\": \"0xd2831bc1b60ce6a0bf71bd89d312b57ef9ad211b4efea3f8bfd38387998d547f5db59ce870a65d16d98a344c85ce0f2e22d371ce455eef0a8c9566f5fa7f71ec2e6ddeb52fcdb0f0287c8b6bbe407f4a3a52bc1b04ea5b978ab698ac1802eb5db482ec1681b1d1d8d1a5e99143a7cde2b85fe29bbe6538066507a91fc8e5ecc6\"\n"
                        + "      }\n"
                        + "    ],\n"
                        + "    \"stateRoot\": \"0x68a77b2364be2f3197bce9ca265a5405ed77904237d8e31dbacfe9e1d3119f77\",\n"
                        + "    \"timestamp\": \"0x174ce4a8931\",\n"
                        + "    \"transactionsRoot\": \"0x60368d2fde59f678e096418d521b53fce8355fb8bca1448d4bb6f5209376e7fc\"\n"
                        + "  }\n"
                        + "}";

        BcosBlock bcosBlock = objectMapper.readValue(blockHeaderStr.getBytes(), BcosBlock.class);
        HashInterface hashInterface = new SM3Digest();
        Assert.assertEquals(
                bcosBlock.getBlock().calculateHash(hashInterface), bcosBlock.getBlock().getHash());
    }

    @Test
    public void testECDSAGetTransactionAndCalculateHash() throws IOException {
        String transactionStr =
                "{\n"
                        + "  \"id\": 1,\n"
                        + "  \"jsonrpc\": \"2.0\",\n"
                        + "  \"result\": {\n"
                        + "    \"blockHash\": \"0xed79502afaf87734f5bc75c2b50d340adc83128afed9dc626a4f5a3cfed837a7\",\n"
                        + "    \"blockLimit\": \"0x100\",\n"
                        + "    \"blockNumber\": \"0x1\",\n"
                        + "    \"chainId\": \"0x1\",\n"
                        + "    \"extraData\": \"0x\",\n"
                        + "    \"from\": \"0xfb257558db8f24ee1c2799df7cc68051fc8d27f7\",\n"
                        + "    \"gas\": \"0x2faf080\",\n"
                        + "    \"gasPrice\": \"0xa\",\n"
                        + "    \"groupId\": \"0x1\",\n"
                        + "    \"hash\": \"0xd8a34a32b86e049fb5e1c0ce89a2a96c34f0c54e622e10abf20d0a0f15bb98cf\",\n"
                        + "    \"input\": \"0x4ed3885e0000000000000000000000000000000000000000000000000000000000000020000000000000000000000000000000000000000000000000000000000000000a464953434f2042434f5300000000000000000000000000000000000000000000\",\n"
                        + "    \"nonce\": \"0x3eb675ec791c2d19858c91d0046821c27d815e2e9c151601203592000016309\",\n"
                        + "    \"signature\": {\n"
                        + "      \"r\": \"0x9edf7c0cb63645442aff11323916d51ec5440de979950747c0189f338afdcefd\",\n"
                        + "      \"s\": \"0x2f3473184513c6a3516e066ea98b7cfb55a79481c9db98e658dd016c37f03dcf\",\n"
                        + "      \"signature\": \"0x9edf7c0cb63645442aff11323916d51ec5440de979950747c0189f338afdcefd2f3473184513c6a3516e066ea98b7cfb55a79481c9db98e658dd016c37f03dcf00\",\n"
                        + "      \"v\": \"0x0\"\n"
                        + "    },\n"
                        + "    \"to\": \"0x8c17cf316c1063ab6c89df875e96c9f0f5b2f744\",\n"
                        + "    \"transactionIndex\": \"0x0\",\n"
                        + "    \"value\": \"0x0\"\n"
                        + "  }\n"
                        + "}";
        BcosTransaction bcosTransaction =
                objectMapper.readValue(transactionStr.getBytes(), BcosTransaction.class);
        Assert.assertEquals(
                bcosTransaction.getTransaction().get().calculateHash(EncryptType.ECDSA_TYPE),
                "0xd8a34a32b86e049fb5e1c0ce89a2a96c34f0c54e622e10abf20d0a0f15bb98cf");
    }

    @Test
    public void testECDSAGetBlockAndCalculateHash() throws IOException {
        String blockHeaderStr =
                "{\n"
                        + "  \"id\": 1,\n"
                        + "  \"jsonrpc\": \"2.0\",\n"
                        + "  \"result\": {\n"
                        + "    \"dbHash\": \"0x0000000000000000000000000000000000000000000000000000000000000000\",\n"
                        + "    \"extraData\": [],\n"
                        + "    \"gasLimit\": \"0x0\",\n"
                        + "    \"gasUsed\": \"0x0\",\n"
                        + "    \"hash\": \"0xed79502afaf87734f5bc75c2b50d340adc83128afed9dc626a4f5a3cfed837a7\",\n"
                        + "    \"logsBloom\": \"0x00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000\",\n"
                        + "    \"number\": 1,\n"
                        + "    \"parentHash\": \"0x4f6394763c33c1709e5a72b202ad4d7a3b8152de3dc698cef6f675ecdaf20a3b\",\n"
                        + "    \"receiptsRoot\": \"0x69a04fa6073e4fc0947bac7ee6990e788d1e2c5ec0fe6c2436d0892e7f3c09d2\",\n"
                        + "    \"sealer\": \"0x3\",\n"
                        + "    \"sealerList\": [\n"
                        + "      \"11e1be251ca08bb44f36fdeedfaeca40894ff80dfd80084607a75509edeaf2a9c6fee914f1e9efda571611cf4575a1577957edfd2baa9386bd63eb034868625f\",\n"
                        + "      \"78a313b426c3de3267d72b53c044fa9fe70c2a27a00af7fea4a549a7d65210ed90512fc92b6194c14766366d434235c794289d66deff0796f15228e0e14a9191\",\n"
                        + "      \"95b7ff064f91de76598f90bc059bec1834f0d9eeb0d05e1086d49af1f9c2f321062d011ee8b0df7644bd54c4f9ca3d8515a3129bbb9d0df8287c9fa69552887e\",\n"
                        + "      \"b8acb51b9fe84f88d670646be36f31c52e67544ce56faf3dc8ea4cf1b0ebff0864c6b218fdcd9cf9891ebd414a995847911bd26a770f429300085f37e1131f36\"\n"
                        + "    ],\n"
                        + "    \"signatureList\": [\n"
                        + "      {\n"
                        + "        \"index\": \"0x0\",\n"
                        + "        \"signature\": \"0x8c274e08c1b86b363634266a9c474a261313ec19ad28bd029465143e9708ef4e74844b6d3e4b1192e290548efe27639398917dfc42195fc81509aa995179895501\"\n"
                        + "      },\n"
                        + "      {\n"
                        + "        \"index\": \"0x1\",\n"
                        + "        \"signature\": \"0x2f25b3cae930b15963745b75bcd12f25837bca336e63f9039e531a505dd85f212b74da6a6530c87052bc8a54d49ee1baae480d32b8b2283cc0b5474f8dd1835400\"\n"
                        + "      },\n"
                        + "      {\n"
                        + "        \"index\": \"0x3\",\n"
                        + "        \"signature\": \"0x97bc872a3beb48d0c373a6a3368ce23086c1c070f29137978f5ac3803b5ef5dc7f9d0d2a377be5995b89a37bc0ccb6cced8a1fcf29b808d7073c2afe819b3be101\"\n"
                        + "      }\n"
                        + "    ],\n"
                        + " \"signatureList\": [\n"
                        + "      {\n"
                        + "        \"index\": \"0x0\",\n"
                        + "        \"signature\": \"0xe6987f9ff8cbee2e1468d257fd09fbc476f2f42163b70bab9ef593a62dc34e4061b10f75e9be6e22e9d55839cf697bab26d9fcc0f3fa4562604bd87dc815f4f000\"\n"
                        + "      },\n"
                        + "      {\n"
                        + "        \"index\": \"0x2\",\n"
                        + "        \"signature\": \"0x49c7f10cd0c766c6100631191a5b989b587cffef65debab0d61b08320de2b9d66a64b56c67046837d9430c9d02c44f1839da9a5aba5759deffbd57e0de052f1a00\"\n"
                        + "      },\n"
                        + "      {\n"
                        + "        \"index\": \"0x3\",\n"
                        + "        \"signature\": \"0x6aaa1fc77260aac4ba37455d02de18fe498323f9473ccf8e44a58a712fb802ce07e028ffaa2e0630d4b2739837c4ebbfd6937264a1050a512f50e340c998ec0500\"\n"
                        + "      }\n"
                        + "    ],"
                        + "    \"stateRoot\": \"0x0000000000000000000000000000000000000000000000000000000000000000\",\n"
                        + "    \"timestamp\": \"0x174cf2bdede\",\n"
                        + "    \"transactionsRoot\": \"0xab7114f4e2930d02852e1578c0a845328e8b69fa8413000d8570483d272937a8\"\n"
                        + "  }\n"
                        + "}";
        BcosBlock bcosBlock = objectMapper.readValue(blockHeaderStr.getBytes(), BcosBlock.class);
        HashInterface hashInterface = new SHA3Digest();
        Assert.assertEquals(
                bcosBlock.getBlock().calculateHash(hashInterface),
                "0xed79502afaf87734f5bc75c2b50d340adc83128afed9dc626a4f5a3cfed837a7");
        // check signatureList
        Assert.assertTrue(bcosBlock.getBlock().getSignatureList().size() == 3);
    }
}
