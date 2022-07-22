import {
  Text,
  Panel,
  ManagedDataInspector,
  createTablePlugin,
  TableBodyRow,
} from 'flipper-plugin';
import React from 'react';

type Row = {
  id: number;
  timestamp: string;
  framework: string;
  type: string;
  name: string;
  params: Map<string, any>;
};

const columns: DataTableColumn<Row>[] = [
  {
    key: 'timestamp',
    title: 'Timestamp',
    width: 100,
  },
  {
    key: 'framework',
    title: 'Framework',
    width: 100,
  },
  {
    key: 'type',
    title: 'Type',
    width: 100,
  },
  {
    key: 'name',
    title: 'Name',
    width: 100,
  },
  {
    key: 'params',
    title: 'Params',
  },
];

function renderSidebar(row: Row) {
  return (
    <div>
        {row["params"]}
        {typeof row["params"]}
    </div>
  );
}

module.exports = createTablePlugin<Row>({
  columns,
  key: 'id',
  method: 'newMeasurement',
  renderSidebar
});