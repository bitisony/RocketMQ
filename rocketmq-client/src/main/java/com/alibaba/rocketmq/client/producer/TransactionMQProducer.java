/**
 * Copyright (C) 2010-2013 Alibaba Group Holding Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.rocketmq.client.producer;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.message.Message;


/**
 * 支持分布式事务Producer
 * 
 * @author shijia.wxr<vintage.wang@gmail.com>
 * @since 2013-7-25
 */
public class TransactionMQProducer extends DefaultMQProducer {
    private TransactionCheckListener transactionCheckListener;
    /**
     * 事务回查最小并发数
     */
    private int checkThreadPoolMinSize = 1;
    /**
     * 事务回查最大并发数
     */
    private int checkThreadPoolMaxSize = 1;
    /**
     * 事务回查队列数
     */
    private int checkRequestHoldMax = 2000;


    public TransactionMQProducer() {
    }


    public TransactionMQProducer(final String producerGroup) {
        super(producerGroup);
    }


    @Override
    public void start() throws MQClientException {
        this.defaultMQProducerImpl.initTransactionEnv();
        super.start();
    }


    @Override
    public void shutdown() {
        super.shutdown();
        this.defaultMQProducerImpl.destroyTransactionEnv();
    }


    public SendResult sendMessageInTransaction(final Message msg, final LocalTransactionExecuter tranExecuter)
            throws MQClientException {
        if (null == this.transactionCheckListener) {
            throw new MQClientException("localTransactionBranchCheckListener is null", null);
        }

        return this.defaultMQProducerImpl.sendMessageInTransaction(msg, tranExecuter);
    }


    public TransactionCheckListener getTransactionCheckListener() {
        return transactionCheckListener;
    }


    public void setTransactionCheckListener(TransactionCheckListener transactionCheckListener) {
        this.transactionCheckListener = transactionCheckListener;
    }


    public int getCheckThreadPoolMinSize() {
        return checkThreadPoolMinSize;
    }


    public void setCheckThreadPoolMinSize(int checkThreadPoolMinSize) {
        this.checkThreadPoolMinSize = checkThreadPoolMinSize;
    }


    public int getCheckThreadPoolMaxSize() {
        return checkThreadPoolMaxSize;
    }


    public void setCheckThreadPoolMaxSize(int checkThreadPoolMaxSize) {
        this.checkThreadPoolMaxSize = checkThreadPoolMaxSize;
    }


    public int getCheckRequestHoldMax() {
        return checkRequestHoldMax;
    }


    public void setCheckRequestHoldMax(int checkRequestHoldMax) {
        this.checkRequestHoldMax = checkRequestHoldMax;
    }
}
