/**
 * Copyright (c) 2009-2015, Data Geekery GmbH (http://www.datageekery.com)
 * All rights reserved.
 *
 * This work is dual-licensed
 * - under the Apache Software License 2.0 (the "ASL")
 * - under the jOOQ License and Maintenance Agreement (the "jOOQ License")
 * =============================================================================
 * You may choose which license applies to you:
 *
 * - If you're using this work with Open Source databases, you may choose
 *   either ASL or jOOQ License.
 * - If you're using this work with at least one commercial database, you must
 *   choose jOOQ License
 *
 * For more information, please visit http://www.jooq.org/licenses
 *
 * Apache Software License 2.0:
 * -----------------------------------------------------------------------------
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * jOOQ License and Maintenance Agreement:
 * -----------------------------------------------------------------------------
 * Data Geekery grants the Customer the non-exclusive, timely limited and
 * non-transferable license to install and use the Software under the terms of
 * the jOOQ License and Maintenance Agreement.
 *
 * This library is distributed with a LIMITED WARRANTY. See the jOOQ License
 * and Maintenance Agreement for more details: http://www.jooq.org/licensing
 */
package org.jooq.util;

import org.jooq.SQLDialect;
// ...
import org.jooq.util.cubrid.CUBRIDDatabase;
// ...
import org.jooq.util.derby.DerbyDatabase;
import org.jooq.util.firebird.FirebirdDatabase;
import org.jooq.util.h2.H2Database;
// ...
import org.jooq.util.hsqldb.HSQLDBDatabase;
// ...
// ...
import org.jooq.util.jdbc.JDBCDatabase;
import org.jooq.util.mariadb.MariaDBDatabase;
import org.jooq.util.mysql.MySQLDatabase;
// ...
import org.jooq.util.postgres.PostgresDatabase;
// ...
import org.jooq.util.sqlite.SQLiteDatabase;
// ...
// ...
// ...

/**
 * A common utility class that provides access to various {@link Database}
 * implementations and {@link SQLDialect}s.
 *
 * @author Lukas Eder
 */
public class Databases {

    /**
     * Get a reference to a {@link Database} class for a given {@link SQLDialect}.
     */
    @SuppressWarnings("deprecation")
    public static final Class<? extends Database> databaseClass(SQLDialect dialect) {
        Class<? extends Database> result = JDBCDatabase.class;

        switch (dialect) {
            /* [pro] xx
            xxxx xxxxxxxxxxx
            xxxx xxxxxxx        xxxxxx x xxxxxxxxxxxxxxxxxxx      xxxxxx
            xxxx xxxx           xxxxxx x xxxxxxxxxxxxxxxxxx       xxxxxx
            xxxx xxxxxx
            xxxx xxxxxxx
            xxxx xxxx           xxxxxx x xxxxxxxxxxxxxxxxxx       xxxxxx
            xxxx xxxxx          xxxxxx x xxxxxxxxxxxxxxxxxxx      xxxxxx
            xxxx xxxxxxxxx      xxxxxx x xxxxxxxxxxxxxxxxxxxxxxx  xxxxxx
            xxxx xxxxxxx        xxxxxx x xxxxxxxxxxxxxxxxxxxxx    xxxxxx
            xxxx xxxxxxxxxx
            xxxx xxxxxxxxxx
            xxxx xxxxxxxxxx
            xxxx xxxxxxx        xxxxxx x xxxxxxxxxxxxxxxxxxxxx    xxxxxx
            xxxx xxxxxxxxx      xxxxxx x xxxxxxxxxxxxxxxxxxxxxxx  xxxxxx
            xxxx xxxxxxxxxxxxxx
            xxxx xxxxxxxxxxxxxx
            xxxx xxxxxxxxxxxxxx
            xxxx xxxxxxxxxx     xxxxxx x xxxxxxxxxxxxxxxxxxxxxxxx xxxxxx
            xxxx xxxxxxx        xxxxxx x xxxxxxxxxxxxxxxxxxxxx    xxxxxx
            xxxx xxxxxxxx       xxxxxx x xxxxxxxxxxxxxxxxxxxxxx   xxxxxx
            xx [/pro] */

            case CUBRID:        result = CUBRIDDatabase.class;    break;
            case DERBY:         result = DerbyDatabase.class;     break;
            case FIREBIRD:      result = FirebirdDatabase.class;  break;
            case H2:            result = H2Database.class;        break;
            case HSQLDB:        result = HSQLDBDatabase.class;    break;
            case MARIADB:       result = MariaDBDatabase.class;   break;
            case MYSQL:         result = MySQLDatabase.class;     break;
            case POSTGRES_9_3:
            case POSTGRES_9_4:
            case POSTGRES:      result = PostgresDatabase.class;  break;
            case SQLITE:        result = SQLiteDatabase.class;    break;

            case DEFAULT:
            case SQL99:         result = JDBCDatabase.class;      break;
        }

        return result;
    }

    /**
     * Get a {@link Database} instance for a given {@link SQLDialect}.
     */
    public static final Database database(SQLDialect dialect) {
        try {
            return databaseClass(dialect).newInstance();
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Cannot create an Database instance for " + dialect, e);
        }
    }
}
