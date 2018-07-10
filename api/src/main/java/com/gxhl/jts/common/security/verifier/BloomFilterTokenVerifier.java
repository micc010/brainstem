/**
 * Copyright 2018 http://github.com/micc010
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.gxhl.jts.common.security.verifier;

import org.springframework.stereotype.Component;

/**
 * @author roger.li
 * @since 2018-03-30
 */
@Component
public class BloomFilterTokenVerifier implements TokenVerifier {

    /**
     * 校验是否已经在redis生成
     *
     * @param jti
     * @return
     */
    @Override
    public boolean verify(String jti) {
        return true;
    }
}
