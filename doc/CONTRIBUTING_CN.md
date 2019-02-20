[English](../CONTRIBUTING.md) / 中文

# 贡献代码

非常感谢能有心为FISCO-BCOS社区贡献代码！

## 分支策略

项目采用[git-flow](https://jeffkreeftmeijer.com/git-flow/)的分支策略。

* master：最新的稳定分支
* dev：待发布的稳定分支
* feature-xxxx：一个正在开发xxxx特性分支
* bugfix-xxxx：一个正在修bug xxxx的分支

## 贡献方法

### Issue

可直接去[issues page](https://github.com/FISCO-BCOS/web3sdk/issues)提issue。

### 修复bug

1. Fork本仓库到个人仓库
2. 从个人仓库的master分支拉出一个bugfix-xxxx分支
3. 在bugfix-xxxx上修复bug
4. 测试修复的bug
5. PR（Pull Request）到本仓库的dev分支
6. 等待社区review这个PR
7. PR合入，bug修复完成！

### 开发新特性

1. Fork本仓库到个人仓库
2. 从个人仓库的dev分支拉出一个feature-xxxx分支
3. 在feature-xxxx上进行特性开发
4. 不定期的从本仓库的dev分支pull最新的改动到feature-xxxx分支
5. 测试新特性
6. PR（Pull Request）到本参考的dev分支
7. 等待社区review这个PR
8. PR合入，特性开发完成！

## 持续集成（CI）

持续集成框架

* travis-ci: [![Build Status](https://travis-ci.org/FISCO-BCOS/web3sdk.svg?branch=release-2.0.1)](https://travis-ci.org/FISCO-BCOS/web3sdk)


代码质量

* Codacy: [![Codacy Badge](https://api.codacy.com/project/badge/Grade/c9769615cc364a408969b088e90d7912)](https://www.codacy.com/app/fisco/web3sdk?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=FISCO-BCOS/web3sdk&amp;utm_campaign=Badge_Grade)



